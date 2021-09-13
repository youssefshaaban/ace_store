package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestLogin
import com.example.mvvm_template.domain.dto.RequestOTP
import com.example.mvvm_template.domain.dto.RequestProfile
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.domain.entity.Authenticate
import com.example.mvvm_template.domain.entity.Profile
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.http.*

interface AccountRepository {
    suspend fun generateOtp(@Body requestOTP: RequestOTP): DataState<Boolean>


    suspend fun login(@Body registerRequest: RequestLogin): DataState<Authenticate>


    suspend fun getProfile(): DataState<Profile>

    suspend fun updateProfile(@Body requestProfile: RequestProfile): DataState<Profile>

    suspend fun logOut(): DataState<Boolean>
}