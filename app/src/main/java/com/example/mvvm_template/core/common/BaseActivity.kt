package com.example.mvvm_template.core.common


import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.error.Failure

import com.example.mvvm_template.ui.LoadingDialog
import com.example.mvvm_template.utils.showLoadingDialog
import com.google.android.material.snackbar.Snackbar
import java.util.*


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private lateinit var mViewDataBinding: T
    var mProgressDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        val dm = resources.displayMetrics
        val conf = resources.configuration
        val lang = "ar"
        conf.setLocale(Locale(lang.toLowerCase())) // API 17+ only.
        resources.updateConfiguration(conf, dm)
        //added
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    }
     fun displayError(message: String?) {
        message?.let {
            Snackbar.make(getViewDataBinding().root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

     fun handleFaluir(error: Failure) {
        when (error) {
            is Failure.UnknownError -> displayError(error.message)
            is Failure.NetworkConnection -> displayError(getString(R.string.check_your_notwrk))
            is Failure.ServerError -> displayError(getString(R.string.something_wron))
        }
    }


    open fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(this)
        mProgressDialog?.showDialog()
    }

    open fun isLoading(): Boolean? {
        return mProgressDialog?.isShowing
    }

    open fun hideLoading() {
        mProgressDialog?.dism()
    }


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.executePendingBindings()

    }

    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}