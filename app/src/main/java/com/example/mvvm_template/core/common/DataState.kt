package com.example.mvvm_template.core.common

import com.example.mvvm_template.domain.error.Failure


sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: Failure) : DataState<Nothing>()
    data class Validation<T>(val enumValidate:T) : DataState<Nothing>()
    object Loading: DataState<Nothing>()
}



