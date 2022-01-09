package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.OrderRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PlaceOrderUseCse @Inject constructor(private val orderRepository: OrderRepository, private val ioDispatcher: CoroutineContext):UseCase<RequestOrder,DataState<Int>>{
    override fun execute(param: RequestOrder): Flow<DataState<Int>> {
        return flow {
            emit(orderRepository.placeOrder(param))
        }.flowOn(ioDispatcher)
    }
}