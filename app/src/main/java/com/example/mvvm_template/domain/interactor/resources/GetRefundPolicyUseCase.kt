package com.example.mvvm_template.domain.interactor.resources

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Resource
import com.example.mvvm_template.domain.entity.ValidationPhone

import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.domain.repository.ResourcesRepo
import com.example.mvvm_template.utils.RegexUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetRefundPolicyUseCase @Inject constructor(
    private val resourcesRepo: ResourcesRepo,
    private val ioDispatcher: CoroutineContext
):GetResourceUseCase {
    override suspend fun getResource(): Flow<DataState<Resource>> {
        return flow { emit(resourcesRepo.getRefundPolicy()) }.flowOn(ioDispatcher)
    }
}