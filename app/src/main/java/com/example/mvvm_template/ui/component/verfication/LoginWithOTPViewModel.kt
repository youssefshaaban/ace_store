package com.example.mvvm_template.ui.component.verfication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt

import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginWithOTPViewModel @Inject constructor(private val loginUseCase: LoginUseCaseWithOt):ViewModel() {
    private val generateOtpLiveDat=SingleLiveEvent<DataState<User>>()
    val generateSuccess:LiveData<DataState<User>> get()=generateOtpLiveDat

    fun verifyOtp(otp:String?,mobileNumber:String?){
        viewModelScope.launch {
            generateOtpLiveDat.value=DataState.Loading
            loginUseCase.execute(LoginUseCaseWithOt.RequestLogin(otp= otp,userName = mobileNumber)).collect {
                generateOtpLiveDat.value=it
            }
        }
    }
    
}