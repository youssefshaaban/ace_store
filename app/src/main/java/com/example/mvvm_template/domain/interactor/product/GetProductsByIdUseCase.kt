package com.example.mvvm_template.domain.interactor.product

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetProductsByIdUseCase @Inject constructor(private val productRepository: ProductRepository, private val ioDispatcher: CoroutineContext):UseCase<Int,DataState<Product>> {
    override fun execute(param: Int): Flow<DataState<Product>> {
        return flow {
            emit(productRepository.getProductById(param))
        }.flowOn(ioDispatcher)
    }
}