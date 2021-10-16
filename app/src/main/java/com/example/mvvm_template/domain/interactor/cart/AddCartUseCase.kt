package com.example.mvvm_template.domain.interactor.cart

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddCartUseCase @Inject constructor(val iCartRepository: ICartRepository,private val ioDispatcher: CoroutineContext) :
    UseCase<RequestAddCart, DataState<Cart>> {
    override fun execute(param: RequestAddCart): Flow<DataState<Cart>> {
        return flow {
            emit(iCartRepository.addToCart(requestAddCart = param))
        }.flowOn(ioDispatcher)
    }
}