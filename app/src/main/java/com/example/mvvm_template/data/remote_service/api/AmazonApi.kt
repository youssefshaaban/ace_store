package com.example.mvvm_template.data.remote_service.api

import ResponseAmazonPaymentApi
import com.example.mvvm_template.BuildConfig
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestPaymentApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AmazonApi {
    @POST(BuildConfig.SDK_TOKEN_URL)
    suspend fun getSDkToken(@Body requestPaymentApi: RequestPaymentApi):Response<ResponseAmazonPaymentApi>
}