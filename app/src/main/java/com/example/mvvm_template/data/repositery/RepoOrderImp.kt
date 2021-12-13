package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.data.remote_service.api.OrderApi
import com.example.mvvm_template.data.remote_service.response.order.toPaymentMethod
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.OrderRepository
import javax.inject.Inject

class RepoOrderImp @Inject constructor(private val orderApi: OrderApi):OrderRepository,BaseDataSource() {
    override suspend fun getPaymentMethod(): DataState<List<PaymentMethod>> {
        val result=getResult {
            orderApi.getPaymentMethod()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { t->t.toPaymentMethod() })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun placeOrder(requestOrder: RequestOrder): DataState<Int> {
        val result=getResult {
            orderApi.placeOrder(requestOrder)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.id)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

}