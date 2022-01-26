package com.example.mvvm_template.ui.component.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityLoginBinding
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.domain.entity.ValidationPhone

import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.utils.getPhoneWithoutZero
import com.example.mvvm_template.utils.hideKeyboard
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.startActivityWithFade
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GenerateOtpActivity : BaseActivity<ActivityLoginBinding>() {
    val viewModel: GenrateOtpViewModel by viewModels()

    @Inject
    lateinit var navigate: AppNavigator
    var isRegistration: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performAction()
        setupObserve()
        viewModel.getCountry()
        getViewDataBinding().newAccount.setOnClickListener {
            navigate.navigateTo(Screen.GENERATE_OTP, Bundle().apply {
                putBoolean("isRegister", true)
            })
        }

    }

    private fun setupObserve() {
        observe(viewModel.generateSuccess, ::handelDataStatGenerate)
        observe(viewModel.observeValidation, ::handleFalidation)
        observe(viewModel.loadingVisiblilty) {
            if (it) {
                showLoading()
            } else
                hideLoading()
        }
        observe(viewModel.error) {
            it?.let {
                handleFaluir(it)
            }
        }
        observe(viewModel.countryLiveDate) {
            it?.let {
                countries->
                getViewDataBinding().countries.adapter = CountryCodeSpinnerAdapter(
                    this, R.layout.item_country,countries
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        isRegistration = intent?.extras?.getBoolean("isRegister")
    }

    private fun handelDataStatGenerate(dataState: DataState<String>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                navigate.navigateTo(Screen.VERIFY_CODE, Bundle().apply {
                    putString(
                        "mobile",
                        getPhoneWithoutZero(getViewDataBinding().phone.text.toString())
                    )
                    isRegistration?.let {
                        putBoolean("isRegister", it)
                    }
                })
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }

    private fun handleFalidation(enumValidate: Any?) {
        when (enumValidate) {
            ValidationPhone.INVALID_PHONE -> displayError(getString(R.string.invalid_phone))
            ValidationPhone.EMPTY_PHONE -> displayError(getString(R.string.phone_is_required))
        }
    }


    private fun performAction() {
        getViewDataBinding().skip.setOnClickListener {
            startActivityWithFade(MainActivity.getIntent(this))
            finish()
        }
        getViewDataBinding().newAccount.setOnClickListener { }
        getViewDataBinding().login.setOnClickListener { performLogin() }

    }

    private fun performLogin() {
        getViewDataBinding().login.hideKeyboard()
        val country=(getViewDataBinding().countries.selectedItem as? Country)
        viewModel.generateOtp(country?.countryCode,getViewDataBinding().phone.text.toString())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    companion object {
        fun getIntent(context: Context): Intent = Intent(context, GenerateOtpActivity::class.java)
    }
}