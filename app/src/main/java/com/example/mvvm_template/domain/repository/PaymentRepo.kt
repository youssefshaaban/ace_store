package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.domain.dto.RequestPaymentApi

interface PaymentRepo {
    suspend fun getMerchantRef():BaseReponse<String>
    suspend fun getSDKToken(paymentApi: RequestPaymentApi)
}