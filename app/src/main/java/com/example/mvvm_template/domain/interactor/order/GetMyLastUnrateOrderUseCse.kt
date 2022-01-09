package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.entity.RateOrder
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.OrderRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetMyLastUnrateOrderUseCse @Inject constructor(private val orderRepository: OrderRepository, private val ioDispatcher: CoroutineContext):UseCase<Unit,DataState<RateOrder?>>{
    override fun execute(param: Unit): Flow<DataState<RateOrder?>> {
        return flow {
            emit(orderRepository.getLastRate())
        }.flowOn(ioDispatcher)
    }
}