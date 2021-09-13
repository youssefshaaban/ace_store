package com.example.mvvm_template.core.common



sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: Failure) : DataState<Nothing>()
    object Loading: DataState<Nothing>()
}


