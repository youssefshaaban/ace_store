package com.example.mvvm_template.domain.interactor.category

import android.util.Log
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetCartegoryPaginationUseCase @Inject constructor(private val categoryRepository: CategoryRepository,private val ioDispatcher: CoroutineContext):UseCase<GetCartegoryPaginationUseCase.ParamPageCategory,DataState<List<Category>>> {
    override fun execute(param: ParamPageCategory): Flow<DataState<List<Category>>> {
        return flow {
            emit(categoryRepository.getCategories(param.pagNumber,param.subCategories))
            Log.d("testThreadCategory",Thread.currentThread().name)
        }.flowOn(ioDispatcher)
    }

    data class ParamPageCategory(val pagNumber:Int,val subCategories:Boolean)
}