package com.example.mvvm_template.data.repositery

import android.util.Log
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.interactor.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.AccountApiService
import com.example.mvvm_template.domain.dto.RequestLogin
import com.example.mvvm_template.domain.dto.RequestOTP
import com.example.mvvm_template.domain.dto.RequestProfile
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.domain.entity.Authenticate
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountRepoImp(private val accountApiService: AccountApiService) :
    AccountRepository, BaseDataSource() {
    override suspend fun generateOtp(requestOTP: RequestOTP): DataState<Boolean> {
           val res=getResult {
                accountApiService.generateOtp(requestOTP)
            }
        return if (res is DataState.Success){
            Log.e("code",res.data.result.code)
            DataState.Success(true)
        }else{
            DataState.Error(res as DataState.Error)
        }
//            return flow {
//                when (result) {
//                    is DataState.Success -> {
//                        emit(DataState.Success(result.data.result))
//                    }
//                    is DataState.Error -> {
//                       emit( DataState.Error(result))
//                    }
//                    else -> {
//                        emit(DataState.Loading)
//                    }
//                }
//            }
    }

    override suspend fun login(registerRequest: RequestLogin): DataState<Authenticate> {
        val res=getResult {
            accountApiService.login(registerRequest)
        }
        return if (res is DataState.Success){
            DataState.Success(Authenticate(accountStatus = res.data.result.accountStatus,
                refreshToken = res.data.result.refreshToken,
                token = res.data.result.token,
                imagePath = res.data.result.imagePath,
                isCompleteRegistration = res.data.result.isCompleteRegistration,
                userId = res.data.result.userId,
                name = res.data.result.name,
                tokenExpirationDate = res.data.result.tokenExpirationDate
                ))
        }else{
            DataState.Error(res as DataState.Error)
        }
    }


    override suspend fun getProfile(): DataState<Profile> {

        val result=getResult {
            accountApiService.getProfile()
        }
        return if (result is DataState.Success){
            DataState.Success(result.data.result)
        }else{
            DataState.Error(result as DataState.Error)
        }
//        return flow {
//            when (result) {
//                is DataState.Success -> {
//                    emit(DataState.Success(result.data.result))
//                }
//                is DataState.Error -> {
//                    emit( DataState.Error(result))
//                }
//                else -> {
//                    emit(DataState.Loading)
//                }
//            }
//        }
    }

    override suspend fun updateProfile(requestProfile: RequestProfile): DataState<Profile> {
       val result=getResult {
            accountApiService.updateProfile(requestProfile)
        }
        return if (result is DataState.Success){
            DataState.Success(result.data.result)
        }else{
            DataState.Error(result as DataState.Error)
        }
//        return flow {
//            when (result) {
//                is DataState.Success -> {
//                    emit(DataState.Success(result.data.result))
//                }
//                is DataState.Error -> {
//                    emit( DataState.Error(result))
//                }
//                else -> {
//                    emit(DataState.Loading)
//                }
//            }
//        }
    }

    override suspend fun logOut():DataState<Boolean> {
        val result=getResult {
            accountApiService.logOut()
        }
        return if (result is DataState.Success){
            DataState.Success(result.data.result)
        }else{
            DataState.Error(result as DataState.Error)
        }
//        return flow {
//            when (result) {
//                is DataState.Success -> {
//                    emit(DataState.Success(result.data.result))
//                }
//                is DataState.Error -> {
//                    emit( DataState.Error(result))
//                }
//                else -> {
//                    emit(DataState.Loading)
//                }
//            }
//        }
    }
}