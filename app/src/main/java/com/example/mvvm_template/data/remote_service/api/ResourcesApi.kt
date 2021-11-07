package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.Resources
import com.example.mvvm_template.data.remote_service.response.cart.Result
import com.example.mvvm_template.domain.dto.RequestAddCart
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ResourcesApi {
    @GET("Resources/AboutUs")
    suspend fun getAbout(): Response<BaseReponse<Resources>>

    @GET("Resources/RefundPolicy")
    suspend fun getRefundPolicy(): Response<BaseReponse<Resources>>

    @GET("Resources/FAQS")
    suspend fun getFAQS(): Response<BaseReponseArray<Resources>>

}