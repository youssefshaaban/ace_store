package com.example.mvvm_template.ui.component.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityLoginBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.utils.startActivityWithFade


class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performAction()
    }

    private fun performAction() {
        getViewDataBinding().skip.setOnClickListener { startActivityWithFade(MainActivity.getIntent(this))
        finishAffinity()
        }
        getViewDataBinding().newAccount.setOnClickListener {  }
        getViewDataBinding().login.setOnClickListener { performLogin() }

    }

    private fun performLogin() {

    }

    override fun getLayoutId(): Int {
       return R.layout.activity_login
    }

    override fun observeViewModel() {

    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }
}