package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.cart.Result
import com.example.mvvm_template.domain.dto.RequestAddCart
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CartApi {
    @POST("Carts")
    suspend fun addCart(@Body requestAddCart: RequestAddCart): Response<BaseReponse<Result>>

    @GET("Carts/GetCart")
    suspend fun getCart(): Response<BaseReponse<Result?>>

}