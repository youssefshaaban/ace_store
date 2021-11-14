package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.*
import com.example.mvvm_template.data.remote_service.response.cart.Result
import com.example.mvvm_template.domain.dto.RequestAddCart
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface ResourcesApi {
    @GET("Resources/AboutUs")
    suspend fun getAbout(): Response<BaseReponse<Resources>>

    @GET("Resources/RefundPolicy")
    suspend fun getRefundPolicy(): Response<BaseReponse<Resources>>

    @GET("Resources/FAQS")
    suspend fun getFAQS(): Response<BaseReponseArray<Resources>>

    @GET("HomeSlides")
    suspend fun getSlider(): Response<CartAndSliderResponse>
}