package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
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
import kotlin.jvm.Throws

class RateOrderUseCse @Inject constructor(
    private val orderRepository: OrderRepository,
    private val ioDispatcher: CoroutineContext
) : UseCase<RateDTO, DataState<Boolean>> {
    @Throws(MissingOrderId::class, MissingRateOrderValue::class, MissingRateOrderComment::class)
    override fun execute(param: RateDTO): Flow<DataState<Boolean>> {
        if (param.orderId == null && param.orderId == 0)
            throw MissingOrderId()
        else if (param.grade == null) {
            throw MissingRateOrderValue()
        } else if (param.comment.isNullOrEmpty()) {
            throw MissingRateOrderComment()
        }
        return flow {
            emit(orderRepository.orderRate(param))
        }.flowOn(ioDispatcher)
    }

    class MissingOrderId : Throwable()
    class MissingRateOrderValue : Throwable()
    class MissingRateOrderComment : Throwable()
}