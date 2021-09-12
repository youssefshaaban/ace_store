package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.data.DataState
import com.example.mvvm_template.data.remote_service.api.AccountApiService
import com.example.mvvm_template.data.remote_service.request.RequestLogin
import com.example.mvvm_template.data.remote_service.request.RequestOTP
import com.example.mvvm_template.data.remote_service.request.RequestProfile
import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.data.remote_service.response.Profile
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface AccountRepositery {
    suspend fun generateOtp(@Body requestOTP: RequestOTP): Flow<DataState<LoginResponse>>

    @POST("api/Account/Login")
    suspend fun login(@Body registerRequest: RequestLogin): Flow<DataState<RequestOTP>>

    @GET("api/Account/Profile")
    suspend fun getProfile(): Flow<DataState<Profile>>

    @PUT("api/Account/Profile")
    suspend fun updateProfile(@Body requestProfile: RequestProfile): Flow<DataState<Profile>>


    @DELETE("api/Account/Logout")
    suspend fun logOut(): Flow<DataState<Boolean>>
}