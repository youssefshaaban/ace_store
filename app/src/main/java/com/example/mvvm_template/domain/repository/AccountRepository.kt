package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestUpdateSetting

import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt
import com.example.mvvm_template.domain.interactor.account.UpdateFirBaseTokenUseCase
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import retrofit2.http.*

interface AccountRepository {
    suspend fun generateOtp(requestOTP: GenerateOtpUseCase.RequestOTP): DataState<Boolean>


    suspend fun login(registerRequest: LoginUseCaseWithOt.RequestLogin): DataState<User>


    suspend fun getProfile(): DataState<Profile>

    suspend fun updateProfile(requestProfile: UpdateProfileUseCase.UpdateRequestProfile): DataState<Profile>

    suspend fun logOut(): DataState<Boolean>

    suspend fun updateFireBaseToken(updateFirBaseTokenUseCase: UpdateFirBaseTokenUseCase.RequestUpdateFirbase):DataState<Boolean>
    suspend fun updateAccountSetting(updateAccountSetting:RequestUpdateSetting):DataState<RequestUpdateSetting>
}