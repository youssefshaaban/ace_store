package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.domain.dto.RequestLogin
import com.example.mvvm_template.domain.dto.RequestOTP
import com.example.mvvm_template.domain.dto.RequestProfile
import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.data.remote_service.response.ReponseOTP
import com.example.mvvm_template.domain.entity.Profile
import retrofit2.Response
import retrofit2.http.*

interface AccountApiService {
    @POST("api/Account/GenerateLoginOTP")
    suspend fun generateOtp(@Body requestOTP: RequestOTP): Response<BaseReponse<ReponseOTP>>

    @POST("api/Account/Login")
    suspend fun login(@Body registerRequest: RequestLogin): Response<BaseReponse<LoginResponse>>

    @GET("api/Account/Profile")
    suspend fun getProfile(): Response<BaseReponse<Profile>>

    @PUT("api/Account/Profile")
    suspend fun updateProfile(@Body requestProfile: RequestProfile): Response<BaseReponse<Profile>>


    @DELETE("api/Account/Logout")
    suspend fun logOut(): Response<BaseReponse<Boolean>>

}