package com.example.mvvm_template.ui.component.main.member_ship

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityMemberShipBinding

import com.example.mvvm_template.databinding.ActivityTransactionPointsBinding
import com.example.mvvm_template.ui.component.main.points.TransactionAdapter
import com.example.mvvm_template.ui.component.main.points.TransactionPointsActivity
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MemberShipActivity : BaseActivity<ActivityMemberShipBinding>() {

    val viewModel:MemberTypesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.menu_member_ship)
        getViewDataBinding().contentRecycle.emptyRecycle.configRecycle(true)
        getViewDataBinding().contentRecycle.emptyRecycle.setEmptyView(getViewDataBinding().contentRecycle.contentEmptyView)
        setupObservable()
        viewModel.getMemberTypes()
    }

    private fun setupObservable() {
        observe(viewModel.loaderVisibilityLiveData){
            if (it)
                showLoading()
            else
                hideLoading()
        }
        observe(viewModel.faluireLiveData,::handleFaluir)
        observe(viewModel.memberTypeLive){
                memberTypes->
            getViewDataBinding().contentRecycle.emptyRecycle.adapter=MemberTypeAdapter(memberTypes)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_member_ship
    }

    companion object{
        fun getIntent(context: Context): Intent = Intent(context, MemberShipActivity::class.java)
    }
}