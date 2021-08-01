package com.example.mvvm_template.ui.component.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivitySignUpBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.utils.showToast

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerOnclickListner()
    }

    private fun registerOnclickListner() {
        getViewDataBinding().register.setOnClickListener {
            if (validateInput()) {

            }
        }
    }

    private fun validateInput(): Boolean {
        if (getViewDataBinding().firstname.text.isBlank()) {
            getViewDataBinding().root.showToast(getString(R.string.validate_must_enter_usern), 1000)
            return false
        }
        if (getViewDataBinding().secondName.text.isBlank()) {
            getViewDataBinding().root.showToast(
                getString(R.string.validate_must_enter_second_name),
                1000
            )
            return false
        }
        if (getViewDataBinding().email.text.isBlank()) {
            getViewDataBinding().root.showToast(getString(R.string.validate_must_enter_email), 1000)
            return false
        }
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_sign_up
    }

    override fun observeViewModel() {

    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, SignUpActivity::class.java)
    }
}