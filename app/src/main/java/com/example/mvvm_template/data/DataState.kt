package com.example.mvvm_template.data

import com.example.mvvm_template.utils.ResourceUpload


sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: ResourceUpload.Failure) : DataState<Nothing>()
    object Loading:DataState<Nothing>()
}


