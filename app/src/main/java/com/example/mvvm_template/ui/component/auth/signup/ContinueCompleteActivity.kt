package com.example.mvvm_template.ui.component.auth.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivitySignUpBinding
import com.example.mvvm_template.domain.dto.FieldsFormValidation
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.observe

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContinueCompleteActivity : BaseActivity<ActivitySignUpBinding>() {

    @Inject
    lateinit var navigator: AppNavigator
    val viewModel: ContinueCompleteViewModel by viewModels()
    val user: User? by lazy {
        intent.extras?.getParcelable("user")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerOnclickListner()
        setupObservable()
    }

    private fun setupObservable() {
        observe(viewModel.profileUpdateSuccess, ::handelDataStatVerifyOTP)
        observe(viewModel.validationExceptionLiveDate, ::handleValidation)
    }

    private fun handleValidation(input: Int) {
        when (input) {
            FieldsFormValidation.EMPTY_EMAIL.value -> displayError(getString(R.string.validate_must_enter_email))
            FieldsFormValidation.INVALID_EMAIL.value -> displayError(getString(R.string.invalide_enter_name))
            FieldsFormValidation.EMPTY_FIRSTNAME.value -> displayError(getString(R.string.validate_must_enter_usern))
            FieldsFormValidation.EMPTY_LASTNAME.value -> displayError(getString(R.string.validate_must_enter_second_name))
        }
    }

    private fun handelDataStatVerifyOTP(dataState: DataState<Profile>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                user?.name = dataState.data.name
                SavePrefs(this, User::class.java).save(user!!)
                navigator.navigateTo(Screen.HOME, null)
                finish()
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }

    private fun registerOnclickListner() {
        getViewDataBinding().register.setOnClickListener {
            SavePrefs(this, User::class.java).save(user!!)
            viewModel.completeRegister(
                UpdateProfileUseCase.UpdateRequestProfile(
                    email = getViewDataBinding().email.text.toString(),
                    firstName = getViewDataBinding().firstname.text.toString(),
                    lastName = getViewDataBinding().secondName.text.toString(),
                    token = "Bearer ${user?.token}"
                )
            )
        }
    }

//    private fun validateInput(): Boolean {
//        if (getViewDataBinding().firstname.text.isBlank()) {
//            displayError(getString(R.string.validate_must_enter_usern))
//            return false
//        }
//        if (getViewDataBinding().secondName.text.isBlank()) {
//            displayError(getString(R.string.validate_must_enter_second_name))
//            return false
//        }
//        if (getViewDataBinding().email.text.isBlank()) {
//            displayError(getString(R.string.validate_must_enter_email))
//            return false
//        }
//        if (!RegexUtils.isValidEmail(getViewDataBinding().email.text.toString())) {
//            displayError(getString(R.string.invalide_enter_name))
//            return false
//        }
//        return true
//    }

    override fun getLayoutId(): Int {
        return R.layout.activity_sign_up
    }


    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, ContinueCompleteActivity::class.java)
    }
}