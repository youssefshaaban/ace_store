package com.example.mvvm_template.domain.interactor.resources

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Resource
import kotlinx.coroutines.flow.Flow

interface GetResourceUseCase {
    suspend fun getResource():Flow<DataState<Resource>>
}