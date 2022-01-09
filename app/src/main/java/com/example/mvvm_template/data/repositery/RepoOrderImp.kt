package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.data.remote_service.api.OrderApi
import com.example.mvvm_template.data.remote_service.response.order.toOrder
import com.example.mvvm_template.data.remote_service.response.order.toPaymentMethod
import com.example.mvvm_template.data.remote_service.response.order.toRateOrder
import com.example.mvvm_template.data.remote_service.response.product.toCurrencyModel
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.*
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.OrderRepository
import javax.inject.Inject

class RepoOrderImp @Inject constructor(private val orderApi: OrderApi) : OrderRepository,
    BaseDataSource() {
    override suspend fun getPaymentMethod(): DataState<List<PaymentMethod>?> {
        val result = getResult {
            orderApi.getPaymentMethod()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result?.map { t ->
                    t.toPaymentMethod().also {
                        if (it.id == 5){
                            it.totalWalletAmount = result.data.totalWalletAmount
                            it.currency=result.data.currency?.toCurrencyModel()
                        }
                        else if (it.id == 6) {
                            it.equivalentPointsAmount = result.data.equivalentPointsAmount
                            it.totalPoint=result.data.totalPoints
                            it.currency=result.data.currency?.toCurrencyModel()
                        }
                    }
                })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun placeOrder(requestOrder: RequestOrder): DataState<Int> {
        val result = getResult {
            orderApi.placeOrder(requestOrder)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.id!!)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getMyOrders(): DataState<List<Order>> {
        val result = getResult {
            orderApi.getMyOrders()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { or -> or.toOrder() })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getOrderById(id: Int): DataState<Order> {
        val result = getResult {
            orderApi.getMyOrderById(id)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toOrder())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getLastRate(): DataState<RateOrder?> {
        val result = getResult {
            orderApi.getLastUnRated()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result?.toRateOrder())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun orderRate(rateDTO: RateDTO): DataState<Boolean> {
        val result = getResult {
            orderApi.rate(rateDTO)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(true)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun reOrder(rateDTO: RateDTO): DataState<Boolean> {
        val result = getResult {
            orderApi.reOrder(rateDTO)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(true)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun skipRate(rateDTO: RateDTO): DataState<Boolean> {
        val result = getResult {
            orderApi.skipRate(rateDTO)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(true)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

}