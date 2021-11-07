package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.domain.dto.RequestPaymentApi
import com.example.mvvm_template.domain.repository.PaymentRepo

class PaymentRepoImp :PaymentRepo {
    override suspend fun getMerchantRef(): BaseReponse<String> {
        return BaseReponse("")
    }

    override suspend fun getSDKToken(paymentApi: RequestPaymentApi) {

    }
}