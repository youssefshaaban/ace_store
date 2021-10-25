package com.example.mvvm_template.ui.component.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.entity.ValidationPhone
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.lookup.GetCountryUseCase

import com.example.mvvm_template.utils.RegexUtils
import com.example.mvvm_template.utils.SingleLiveEvent
import com.example.mvvm_template.utils.getPhoneWithoutZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenrateOtpViewModel @Inject constructor(
    private val generateOtpUseCase: GenerateOtpUseCase,
    private val getCountryUseCase: GetCountryUseCase
) :
    ViewModel() {
    private val generateOtpLiveDat = SingleLiveEvent<DataState<Boolean>>()
    val generateSuccess: LiveData<DataState<Boolean>> get() = generateOtpLiveDat
    private val validationOtpLiveDat = SingleLiveEvent<ValidationPhone>()
    val observeValidation: LiveData<ValidationPhone> get() = validationOtpLiveDat
    val loadingVisiblilty = MutableLiveData(false)
    val countryLiveDate = MutableLiveData<List<Country>>()
    val error = SingleLiveEvent<Failure>()

    fun generateOtp(countryCode:String?,mobileNumber: String) {
        val param = GenerateOtpUseCase.RequestOTP(countryCode ?: "", getPhoneWithoutZero(mobileNumber))
        if (validate(param) == null) {
            viewModelScope.launch {
                generateOtpLiveDat.value = DataState.Loading
                generateOtpUseCase.execute(param).collect {
                    generateOtpLiveDat.value = it
                }
            }
        }

    }

    fun getCountry() {
        viewModelScope.launch {
            loadingVisiblilty.value = true
            getCountryUseCase.execute(Unit).collect {
                loadingVisiblilty.value = false
                if (it is DataState.Success) {
                    countryLiveDate.value = it.data
                } else if (it is DataState.Error) {
                    error.value = it.error
                }
            }
        }
    }


    fun validate(requestOTP: GenerateOtpUseCase.RequestOTP): ValidationPhone? {
        if (requestOTP.mobileNumber.isEmpty()) {
            validationOtpLiveDat.value = ValidationPhone.EMPTY_PHONE
            return ValidationPhone.EMPTY_PHONE
        }
        return null
    }

}