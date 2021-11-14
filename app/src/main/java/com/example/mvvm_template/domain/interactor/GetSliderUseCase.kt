package com.example.mvvm_template.domain.interactor

import android.util.Log
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.HomeData
import com.example.mvvm_template.domain.entity.Resource
import com.example.mvvm_template.domain.entity.Slider

import com.example.mvvm_template.domain.repository.ResourcesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetSliderUseCase @Inject constructor(
    private val resourcesRepo: ResourcesRepo,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun getSlider(): Flow<DataState<HomeData>> {
        return flow { emit(resourcesRepo.getSlider())
        }.flowOn(ioDispatcher)
    }
}