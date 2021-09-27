package com.example.mvvm_template.domain.interactor.category

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetCardUseCase @Inject constructor(private val categoryRepository: CategoryRepository,private val ioDispatcher: CoroutineContext):UseCase<Unit,DataState<List<Card>>> {
    override fun execute(param: Unit): Flow<DataState<List<Card>>> {
        return flow {
            emit(categoryRepository.getCards())
        }.flowOn(ioDispatcher)
    }
}