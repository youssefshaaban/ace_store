package com.example.mvvm_template.core.common

import android.content.Context
import android.content.Intent
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


abstract class BaseDialog<T : ViewDataBinding> : DialogFragment() {

    private var mActivity: BaseActivity<*>? = null
  //  private lateinit var mRootView: View
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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    fun handleFaluir(error: Failure) {
        if (mActivity != null) {
            mActivity?.handleFaluir(error)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }

    open fun showloading() {
        if (mActivity != null) {
            mActivity?.showLoading()
        }
    }


    open fun hidLoading() {
        if (mActivity != null) {
            mActivity?.hideLoading()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        observeViewModel()
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