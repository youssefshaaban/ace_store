package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.order.LastRatedResponse
import com.example.mvvm_template.data.remote_service.response.order.OrderResponse
import com.example.mvvm_template.data.remote_service.response.order.PaymentMethodReponse
import com.example.mvvm_template.data.remote_service.response.order.PaymentReponse
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestOrder
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {

    @GET("Order/PaymentMethods")
    suspend fun getPaymentMethod(): Response<PaymentReponse>

    @POST("Order/PlaceOrder")
    suspend fun placeOrder(@Body requestOrder: RequestOrder): Response<BaseReponse<OrderResponse>>

    @GET("Orders/Me")
    suspend fun getMyOrders() :Response<BaseReponseArray<OrderResponse>>
    @GET("Orders")
    suspend fun getMyOrderById(@Query("Id") id:Int) :Response<BaseReponse<OrderResponse>>

    @GET("Orders/LastUnRated")
    suspend fun getLastUnRated():Response<BaseReponse<LastRatedResponse?>>

    @POST("Orders/Rate")
    suspend fun rate(@Body rateDTO: RateDTO):Response<BaseReponse<Boolean>>

    @POST("Orders/SkipRate")
    suspend fun skipRate(@Body rateDTO: RateDTO):Response<BaseReponse<Boolean>>
    @POST("Order/ReOrder")
    suspend fun reOrder(@Body rateDTO: RateDTO):Response<BaseReponse<Boolean>>
}