package com.example.mvvm_template.data.repositery

import android.util.Log
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapProfileReponseToProfile
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.AccountApiService
import com.example.mvvm_template.domain.dto.RequestUpdateSetting

import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt
import com.example.mvvm_template.domain.interactor.account.UpdateFirBaseTokenUseCase
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepoImp @Inject constructor(private val accountApiService: AccountApiService) :
    AccountRepository, BaseDataSource() {
    private val mapProfileReponseToProfile=MapProfileReponseToProfile()
    override suspend fun generateOtp(requestOTP: GenerateOtpUseCase.RequestOTP): DataState<Boolean> {
           val res=getResult {
                accountApiService.generateOtp(requestOTP)
            }
        return if (res is DataState.Success){
            Log.e("code",res.data.result.code)
            DataState.Success(true)
        }else{
            DataState.Error((res as DataState.Error).error)
        }
    }

    override suspend fun login(registerRequest: LoginUseCaseWithOt.RequestLogin): DataState<User> {
        val res=getResult {
            accountApiService.login(registerRequest)
        }
        return if (res is DataState.Success){
            DataState.Success(User(accountStatus = res.data.result.accountStatus,
                refreshToken = res.data.result.refreshToken,
                token = res.data.result.token,
                imagePath = res.data.result.imagePath,
                userId = res.data.result.userId,
                name = res.data.result.name,
                tokenExpirationDate = res.data.result.tokenExpirationDate
                ))
        }else{
            DataState.Error((res as DataState.Error).error)
        }
    }


    override suspend fun getProfile(): DataState<Profile> {

        val result=getResult {
            accountApiService.getProfile()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(mapProfileReponseToProfile.map(result.data.result))
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun updateProfile(requestProfile: UpdateProfileUseCase.UpdateRequestProfile): DataState<Profile> {
        val result=getResult {
            accountApiService.updateProfile(requestProfile)
        }
        return if (result is DataState.Success){
            DataState.Success(mapProfileReponseToProfile.map(result.data.result))
        }else{
            DataState.Error((result as DataState.Error).error)
        }
    }


    override suspend fun logOut():DataState<Boolean> {
        val result=getResult {
            accountApiService.logOut()
        }
        return if (result is DataState.Success){
            DataState.Success(result.data.result)
        }else{
            DataState.Error((result as DataState.Error).error)
        }
    }

    override suspend fun updateFireBaseToken(updateFirBaseTokenUseCase: UpdateFirBaseTokenUseCase.RequestUpdateFirbase):DataState<Boolean> {
        val result=getResult {
            accountApiService.updateFireBase(updateFirBaseTokenUseCase)
        }
        return if (result is DataState.Success){
            DataState.Success(result.data.result)
        }else{
            DataState.Error((result as DataState.Error).error)
        }
    }

    override suspend fun updateAccountSetting(updateAccountSetting: RequestUpdateSetting): DataState<Boolean> {
        val result=getResult {
            accountApiService.updateAccountSetting(updateAccountSetting)
        }
        return if (result is DataState.Success){
            DataState.Success(true)
        }else{
            DataState.Error((result as DataState.Error).error)
        }
    }
}