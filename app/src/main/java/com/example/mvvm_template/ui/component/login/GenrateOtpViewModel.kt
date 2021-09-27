package com.example.mvvm_template.ui.component.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.ValidationPhone
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase

import com.example.mvvm_template.utils.RegexUtils
import com.example.mvvm_template.utils.SingleLiveEvent
import com.example.mvvm_template.utils.getPhoneWithoutZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenrateOtpViewModel @Inject constructor(private val generateOtpUseCase: GenerateOtpUseCase) :
    ViewModel() {
    private val generateOtpLiveDat = SingleLiveEvent<DataState<Boolean>>()
    val generateSuccess: LiveData<DataState<Boolean>> get() = generateOtpLiveDat
    private val validationOtpLiveDat = SingleLiveEvent<ValidationPhone>()
    val observeValidation: LiveData<ValidationPhone> get() = validationOtpLiveDat

    fun generateOtp(mobileNumber: String) {
        val param = GenerateOtpUseCase.RequestOTP("966", getPhoneWithoutZero(mobileNumber))
        if (validate(param) == null) {
            viewModelScope.launch {
                generateOtpLiveDat.value = DataState.Loading
                generateOtpUseCase.execute(param).collect {
                    generateOtpLiveDat.value = it
                }
            }
        }

    }


    fun validate(requestOTP: GenerateOtpUseCase.RequestOTP): ValidationPhone? {
        if (requestOTP.mobileNumber.isEmpty()) {
            validationOtpLiveDat.value = ValidationPhone.EMPTY_PHONE
            return ValidationPhone.EMPTY_PHONE
        } else if (RegexUtils.isValidPhoneNumber("966", requestOTP.mobileNumber)) {
            validationOtpLiveDat.value = ValidationPhone.INVALID_PHONE
            return ValidationPhone.INVALID_PHONE
        }
        return null
    }

}