package com.example.mvvm_template.core.common


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.error.Failure

import com.example.mvvm_template.ui.LoadingDialog
import com.example.mvvm_template.ui.component.auth.login.GenerateOtpActivity
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.showLoadingDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

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
            is Failure.UnAuthorize -> {
                SavePrefs(this, User::class.java).clear()
                startActivity(
                    Intent(this, GenerateOtpActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
            }
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
    private fun showMustSignInPopUp() {
        MaterialAlertDialogBuilder(getViewDataBinding().root.context)
            .setTitle(getString(R.string.login))
            .setMessage(getString(R.string.must_login))
            .setPositiveButton(getString(R.string.ok)){
                    dialog,_->
                // open_setting_screen
                openSignIn()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)){
                    dialog,_->
                dialog.dismiss()
            }
            .show()
    }

    private fun openSignIn() {
        startActivity(GenerateOtpActivity.getIntent(this).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finishAffinity()
    }

    fun checkUserLogin(): Boolean {
        if (App.getUser() == null) {
            showMustSignInPopUp()
            return false
        }
        return true
    }

}