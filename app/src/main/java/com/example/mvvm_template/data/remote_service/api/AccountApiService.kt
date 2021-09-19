package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.data.remote_service.response.ProfileResponse
import com.example.mvvm_template.data.remote_service.response.ReponseOTP
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import retrofit2.Response
import retrofit2.http.*

interface AccountApiService {
    @POST("Account/GenerateLoginOTP")
    suspend fun generateOtp(@Body requestOTP: GenerateOtpUseCase.RequestOTP): Response<BaseReponse<ReponseOTP>>

    @POST("Account/Login")
    suspend fun login(@Body registerRequest: LoginUseCaseWithOt.RequestLogin): Response<BaseReponse<LoginResponse>>

    @GET("Account/Profile")
    suspend fun getProfile(): Response<BaseReponse<ProfileResponse>>

    @PUT("Account/Profile")

    suspend fun updateProfile(@Header("Authorization") token:String,@Body requestProfile: UpdateProfileUseCase.UpdateRequestProfile): Response<BaseReponse<ProfileResponse>>


    @DELETE("Account/Logout")
    suspend fun logOut(): Response<BaseReponse<Boolean>>

}