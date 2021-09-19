package com.example.mvvm_template.ui.component.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase

import com.example.mvvm_template.utils.RegexUtils
import com.example.mvvm_template.utils.SingleLiveEvent
import com.example.mvvm_template.utils.getPhoneWithoutZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenrateOtpViewModel @Inject constructor(private val generateOtpUseCase: GenerateOtpUseCase):ViewModel() {
    private val generateOtpLiveDat=SingleLiveEvent<DataState<Boolean>>()
    val generateSuccess:LiveData<DataState<Boolean>> get()=generateOtpLiveDat

    fun generateOtp(mobileNumber:String){
        viewModelScope.launch {
            generateOtpLiveDat.value=DataState.Loading
            generateOtpUseCase.execute(GenerateOtpUseCase.RequestOTP("966", getPhoneWithoutZero(mobileNumber))).collect {
                generateOtpLiveDat.value=it
            }
        }
    }

}