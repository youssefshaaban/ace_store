package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.AmazonApi
import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.domain.dto.RequestPaymentApi
import com.example.mvvm_template.domain.entity.AmazonSdk
import com.example.mvvm_template.domain.repository.PaymentRepo
import kotlinx.coroutines.delay
import toAmazonSdk
import java.util.*
import javax.inject.Inject

class PaymentRepoImp @Inject constructor(val amazonApi: AmazonApi) : PaymentRepo,
    BaseDataSource() {
    override suspend fun getMerchantRef(): BaseReponse<String> {
        delay(2000)
        return BaseReponse("${Date().time}")
    }

    override suspend fun getSDKToken(paymentApi: RequestPaymentApi): DataState<AmazonSdk> {
        val result = getResult { amazonApi.getSDkToken(paymentApi) }
        if (result is DataState.Success) {
            return DataState.Success(result.data.toAmazonSdk())
        }
        return DataState.Error((result as DataState.Error).error)
    }


}