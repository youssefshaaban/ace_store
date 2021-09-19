package com.example.mvvm_template.core.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.error.Failure


abstract class BaseFragment<T : ViewDataBinding> : DialogFragment() {

    private var mActivity: BaseActivity<*>? = null

    private lateinit var mViewDataBinding: T
    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun observeViewModel()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.mActivity = context
        }
    }

    fun handleFaluir(error: Failure) {
        when (error) {
            is Failure.UnknownError -> mActivity?.displayError(error.message)
            is Failure.NetworkConnection -> mActivity?.displayError(getString(R.string.check_your_notwrk))
            is Failure.ServerError -> mActivity?.displayError(getString(R.string.something_wron))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }




    open fun hideLoading() {
        if (mActivity != null) {
            mActivity?.hideLoading()
        }
    }

    open fun showLoading() {
        if (mActivity != null) {
            mActivity?.showLoading()
        }
    }

    open fun isLoading():Boolean? {
        if (mActivity != null) {
            return mActivity?.isLoading()
        }
        return null

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
       // mActivity?.initMessageObservable()
        super.onActivityCreated(savedInstanceState)
    }



    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun getViewDataBinding(): T {
        return mViewDataBinding
    }


}