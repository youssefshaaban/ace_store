package com.example.mvvm_template.data.remote_service.api

import ResponseAmazonPaymentApi
import com.example.mvvm_template.BuildConfig
import retrofit2.Response
import retrofit2.http.POST

interface AmazonApi {
    @POST(BuildConfig.SDK_TOKEN_URL)
    suspend fun getSDkToken():ResponseAmazonPaymentApi

}