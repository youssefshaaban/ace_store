package com.example.mvvm_template.domain.interactor

import kotlinx.coroutines.flow.Flow

interface UseCase<T,R> {
    fun execute(param : T?) : Flow<R>
}