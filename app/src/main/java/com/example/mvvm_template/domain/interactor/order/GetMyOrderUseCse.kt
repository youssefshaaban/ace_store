package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.OrderRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMyOrderUseCse @Inject constructor(private val orderRepository: OrderRepository, private val ioDispatcher: CoroutineContext):UseCase<Unit,DataState<List<Order>>>{
    override fun execute(param: Unit): Flow<DataState<List<Order>>> {
        return flow {
            emit(orderRepository.getMyOrders())
        }.flowOn(ioDispatcher)
    }
}