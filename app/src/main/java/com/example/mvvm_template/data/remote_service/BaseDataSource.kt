package com.example.mvvm_template.data.remote_service


import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.error.Failure
import com.google.gson.Gson

import retrofit2.Response
import java.net.HttpURLConnection
import java.net.UnknownHostException

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataState<T> {
        try {
            val response = call()
            val responseCode = response.code()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return DataState.Success(body)
            }
            val errorStr = response.errorBody()?.string()
            var errorObjects:BasicError? = null
            if (!errorStr.isNullOrEmpty()) {
                errorObjects = Gson().fromJson(errorStr, BasicError::class.java)

            }
            return when (responseCode) {
                HttpURLConnection.HTTP_UNAUTHORIZED->DataState.Error(Failure.UnAuthorize)
                HttpURLConnection.HTTP_NOT_FOUND,
                HttpURLConnection.HTTP_FORBIDDEN,HttpURLConnection.HTTP_UNAVAILABLE
                    ,HttpURLConnection.HTTP_INTERNAL_ERROR->DataState.Error(error = Failure.ServerError(errorObjects?.detail))
                else -> DataState.Error(error = Failure.UnknownError(errorObjects?.detail))
            }

        } catch (e: Exception) {
            return when (e) {
                is UnknownHostException -> DataState.Error(error = Failure.NetworkConnection)
                else -> {
                    DataState.Error(error = Failure.UnknownError(e.toString()))
                }
            }
        }
    }


    data class BasicError(
        val status: Int,
        val title: String,
        val traceId: String,
        val detail:String,
        val type: String
    )

}