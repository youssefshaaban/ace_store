package com.example.mvvm_template.ui.component.main.points

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityPointsBinding
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointsActivity : BaseActivity<ActivityPointsBinding>() {
    val viewModel:PointsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.menu_status)
        getViewDataBinding().rvPoints.configRecycle(true)
        setupObservable()
        viewModel.getPoints()
        getViewDataBinding().totalPoints.setOnClickListener {
            startActivity(TransactionPointsActivity.getIntent(this))
        }
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
            getViewDataBinding().point=point
            getViewDataBinding().totalPoints.setOnClickListener { startActivity(TransactionPointsActivity.getIntent(this)) }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_points
    }

    companion object{
        fun getIntent(context: Context): Intent = Intent(context, PointsActivity::class.java)
    }
}