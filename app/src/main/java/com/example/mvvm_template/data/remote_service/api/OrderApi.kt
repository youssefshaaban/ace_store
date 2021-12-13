package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.CountryResponse
import com.example.mvvm_template.data.remote_service.response.order.OrderReponse
import com.example.mvvm_template.data.remote_service.response.order.PaymentMethodReponse
import com.example.mvvm_template.domain.dto.RequestOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApi {

    @GET("Order/PaymentMethods")
    suspend fun getPaymentMethod(): Response<BaseReponseArray<PaymentMethodReponse>>

    @POST("Order/PlaceOrder")
    suspend fun placeOrder(requestOrder: RequestOrder): Response<BaseReponse<OrderReponse>>

}