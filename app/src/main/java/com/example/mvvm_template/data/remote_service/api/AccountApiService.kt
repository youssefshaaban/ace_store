package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.request.RequestLogin
import com.example.mvvm_template.data.remote_service.request.RequestOTP
import com.example.mvvm_template.data.remote_service.request.RequestProfile
import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.data.remote_service.response.Profile
import retrofit2.Response
import retrofit2.http.*

interface AccountApiService {
    @POST("api/Account/GenerateLoginOTP")
    suspend fun generateOtp(@Body requestOTP: RequestOTP): Response<BaseReponse<LoginResponse>>

    @POST("api/Account/Login")
    suspend fun login(@Body registerRequest: RequestLogin): Response<BaseReponse<RequestOTP>>

    @GET("api/Account/Profile")
    suspend fun getProfile(): Response<BaseReponse<Profile>>

    @PUT("api/Account/Profile")
    suspend fun updateProfile(@Body requestProfile: RequestProfile): Response<BaseReponse<Profile>>


    @DELETE("api/Account/Logout")
    suspend fun logOut(): Response<BaseReponse<Boolean>>

}