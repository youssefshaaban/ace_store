package com.example.mvvm_template.domain.interactor


import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.error.Failure

import retrofit2.Response
import java.net.HttpURLConnection
import java.net.UnknownHostException

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataState<T> {
        try {
            val response = call()
            val responseCode=response.code()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return DataState.Success(body)
            }
            return when(responseCode){
                HttpURLConnection.HTTP_NOT_FOUND-> DataState.Error(error = Failure.ServerError.NotFound)
                HttpURLConnection.HTTP_FORBIDDEN-> DataState.Error(error = Failure.ServerError.AccessDenied)
                HttpURLConnection.HTTP_UNAVAILABLE-> DataState.Error(error = Failure.ServerError.ServiceUnavailable)
                HttpURLConnection.HTTP_INTERNAL_ERROR-> DataState.Error(error = Failure.ServerError.InternalServerError)
                else-> DataState.Error(error = Failure.UnknownError)
            }
        } catch (e: Exception) {
           return when(e){
                is UnknownHostException-> DataState.Error(error = Failure.NetworkConnection)
                else ->{
                    DataState.Error(error = Failure.NetworkConnection)
                }
            }
        }
    }

}