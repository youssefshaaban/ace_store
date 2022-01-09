package com.example.mvvm_template.ui.component.auth.verfication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityVerficationBinding
import com.example.mvvm_template.domain.entity.AccountStatus
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.entity.ValidationPhone
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.hideKeyboard
import com.example.mvvm_template.utils.observe
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject


@AndroidEntryPoint
class LogiWithOtpActivity : BaseActivity<ActivityVerficationBinding>() {
    @Inject
    lateinit var navigator: AppNavigator
    val viewModel: LoginWithOTPViewModel by viewModels()
    val mobileNumber: String? by lazy {
        intent.extras?.getString("mobile")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().send.setOnClickListener {
            viewModel.verifyOtp(getViewDataBinding().txtPinEntry.text.toString(), mobileNumber)
        }
        getViewDataBinding().txtPinEntry.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length==6){
                    viewModel.verifyOtp(getViewDataBinding().txtPinEntry.text.toString(), mobileNumber)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        setupObserve()
    }

    private fun setupObserve() {
        observe(viewModel.generateSuccess, ::handelDataStatVerifyOTP)
        observe(viewModel.observeValidation,::handleFalidation)
    }

    private fun handelDataStatVerifyOTP(dataState: DataState<User>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                handelNavigation(dataState.data)
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }

        }
    }

    private fun handelNavigation(data: User) {
        when (data.accountStatus) {
            AccountStatus.CompletedRegistration.status -> {
                // go to home
                navigator.navigateTo(Screen.HOME, null)
                SavePrefs(this, User::class.java).save(data)
                finishAffinity()
            }
            AccountStatus.PendingCompleteRegistration.status -> {
                // go to update profile
                navigator.navigateTo(Screen.COMPLETE_REGISTRATION, Bundle().apply {
                    putParcelable("user", data)
                })
                finish()
            }
            AccountStatus.PendingConfirmMobile.status -> {
//                navigator.navigateTo(Screen.COMPLETE_REGISTRATION, Bundle().apply {
//                    putParcelable("user", data)
//                })
            }
        }
    }

    @SuppressLint("CheckResult")
//    private fun handleWritingCode() {
//        getViewDataBinding().etFirst.requestFocus()
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etFirst)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etSecondKey.requestFocus()
//                }
//            }
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etSecondKey)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etThirdKey.requestFocus()
//                }
//            }
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etThirdKey)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etForthKey.requestFocus()
//                }
//            }
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etForthKey)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etFifthKey.requestFocus()
//                }
//            }
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etFifthKey)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etSexKey.requestFocus()
//                }
//            }
//        RxTextView.afterTextChangeEvents(getViewDataBinding().etSexKey)
//            .subscribe { textViewAfterTextChangeEvent ->
//                if (!textViewAfterTextChangeEvent.editable().toString().isEmpty()) {
//                    getViewDataBinding().etSexKey.hideKeyboard()
//                    viewModel.verifyOtp(getOtp(), mobileNumber)
//                }
//            }
//
//    }


    private fun handleFalidation(enumValidate: Any?) {
        when (enumValidate) {
            ValidationPhone.INVALID_PHONE -> displayError(getString(R.string.invalid_phone))
            ValidationPhone.EMPTY_PHONE -> displayError(getString(R.string.phone_is_required))
            ValidationPhone.EMPTY_OTP->displayError(getString(R.string.must_enter_code))
        }
    }

//    private fun getOtp(): String {
//        val stringBuilder = StringBuilder(getViewDataBinding().etFirst.text.toString())
//        stringBuilder.append(getViewDataBinding().etSecondKey.text.toString())
//        stringBuilder.append(getViewDataBinding().etThirdKey.text.toString())
//        stringBuilder.append(getViewDataBinding().etForthKey.text.toString())
//        stringBuilder.append(getViewDataBinding().etFifthKey.text.toString())
//        stringBuilder.append(getViewDataBinding().etSexKey.text.toString())
//
//        return stringBuilder.toString()
//    }


    companion object {
        fun getIntent(context: Context): Intent = Intent(context, LogiWithOtpActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verfication
    }
}