package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.response.BaseReponseArrayPagination
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.*

import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import retrofit2.http.*

interface OrderRepository {
    suspend fun getPaymentMethod(): DataState<List<PaymentMethod>?>
    suspend fun placeOrder(requestOrder: RequestOrder): DataState<Int>
    suspend fun getMyOrders(): DataState<List<Order>>
    suspend fun getOrderById(id:Int): DataState<Order>
    suspend fun getLastRate(): DataState<RateOrder?>
    suspend fun orderRate(rateDTO: RateDTO): DataState<Boolean>
    suspend fun reOrder(rateDTO: RateDTO): DataState<Boolean>
    suspend fun skipRate(rateDTO: RateDTO): DataState<Boolean>
}