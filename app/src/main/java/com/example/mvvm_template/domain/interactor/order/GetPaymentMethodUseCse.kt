package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.OrderRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetPaymentMethodUseCse(private val orderRepository: OrderRepository, private val ioDispatcher: CoroutineContext):UseCase<Unit,DataState<List<PaymentMethod>>>{
    override fun execute(param: Unit): Flow<DataState<List<PaymentMethod>>> {
        return flow {
            emit(orderRepository.getPaymentMethod())
        }.flowOn(ioDispatcher)
    }
}