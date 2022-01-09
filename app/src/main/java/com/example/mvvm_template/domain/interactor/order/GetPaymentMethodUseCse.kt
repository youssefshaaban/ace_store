package com.example.mvvm_template.domain.interactor.order

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.OrderRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetPaymentMethodUseCse @Inject constructor(private val orderRepository: OrderRepository, private val ioDispatcher: CoroutineContext):UseCase<Unit,DataState<List<PaymentMethod>?>>{
    override fun execute(param: Unit): Flow<DataState<List<PaymentMethod>?>> {
        return flow {
            val data=orderRepository.getPaymentMethod()
            if (data is DataState.Success){
                emit(DataState.Success(data.data?.filter { t->t.id!=3}))
            }else if(data is DataState.Error) {
                emit(DataState.Error(data.error))
            }
        }.flowOn(ioDispatcher)
    }
}