package com.example.mvvm_template.ui.component.main.points

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityTransactionPointsBinding
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionPointsActivity : BaseActivity<ActivityTransactionPointsBinding>() {
    val viewModel:PointsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.total_points)
        getViewDataBinding().contentRecycle.emptyRecycle.configRecycle(true)
        getViewDataBinding().contentRecycle.emptyRecycle.setEmptyView(getViewDataBinding().contentRecycle.contentEmptyView)
        setupObservable()
        viewModel.getPoints()
    }
    private fun setupObservable() {
        observe(viewModel.loaderVisibilityLiveData){
            if (it)
                showLoading()
            else
                hideLoading()
        }
        observe(viewModel.faluireLiveData,::handleFaluir)
        observe(viewModel.pointsLiveData){
                point->
            point.transactions?.let {
                getViewDataBinding().contentRecycle.emptyRecycle.adapter=TransactionAdapter(it)
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_transaction_points
    }
    companion object{
        fun getIntent(context: Context): Intent = Intent(context, TransactionPointsActivity::class.java)
    }
}