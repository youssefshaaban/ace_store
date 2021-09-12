package com.example.mvvm_template.data.repositery.account

import com.example.mvvm_template.data.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.AccountApiService
import com.example.mvvm_template.data.remote_service.request.RequestLogin
import com.example.mvvm_template.data.remote_service.request.RequestOTP
import com.example.mvvm_template.data.remote_service.request.RequestProfile
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.data.remote_service.response.Profile
import com.example.mvvm_template.data.repositery.AccountRepositery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext

class AccountRepo(private val accountApiService: AccountApiService, val ioDispatcher: CoroutineContext) :AccountRepositery, BaseDataSource() {
    override suspend fun generateOtp(requestOTP: RequestOTP): Flow<DataState<LoginResponse>> {
           val result=getResult {
                accountApiService.generateOtp(requestOTP)
            }
            return flow {
                when (result) {
                    is DataState.Success -> {
                        emit(DataState.Success(result.data.result))
                    }
                    is DataState.Error -> {
                       emit( DataState.Error(result))
                    }
                    else -> {
                        emit(DataState.Loading)
                    }
                }
            }
    }

    override suspend fun login(registerRequest: RequestLogin): Flow<DataState<RequestOTP>> {
        val result=getResult {
            accountApiService.login(registerRequest)
        }
        return flow {
            when (result) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.result))
                }
                is DataState.Error -> {
                    emit( DataState.Error(result))
                }
                else -> {
                    emit(DataState.Loading)
                }
            }
        }
    }

    override suspend fun getProfile(): Flow<DataState<Profile>> {
        val result=getResult {
            accountApiService.getProfile()
        }
        return flow {
            when (result) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.result))
                }
                is DataState.Error -> {
                    emit( DataState.Error(result))
                }
                else -> {
                    emit(DataState.Loading)
                }
            }
        }
    }

    override suspend fun updateProfile(requestProfile: RequestProfile): Flow<DataState<Profile>> {
        val result=getResult {
            accountApiService.updateProfile(requestProfile)
        }
        return flow {
            when (result) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.result))
                }
                is DataState.Error -> {
                    emit( DataState.Error(result))
                }
                else -> {
                    emit(DataState.Loading)
                }
            }
        }
    }

    override suspend fun logOut(): Flow<DataState<Boolean>> {
        val result=getResult {
            accountApiService.logOut()
        }
        return flow {
            when (result) {
                is DataState.Success -> {
                    emit(DataState.Success(result.data.result))
                }
                is DataState.Error -> {
                    emit( DataState.Error(result))
                }
                else -> {
                    emit(DataState.Loading)
                }
            }
        }
    }
}