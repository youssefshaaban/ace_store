package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.BaseReponseArrayPagination
import com.example.mvvm_template.data.remote_service.response.ReponseOTP
import com.example.mvvm_template.data.remote_service.response.category.CategoryResponse
import com.example.mvvm_template.data.remote_service.response.product.ProductByIdResponse
import com.example.mvvm_template.data.remote_service.response.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ProductsApi {
    @GET("Products/{id}")
    suspend fun getProductById(@Path("id")id:Int): Response<BaseReponse<ProductByIdResponse>>
    @GET("Products/Paged")
    suspend fun getProductsPage(@QueryMap map:Map<String,String>): Response<BaseReponseArrayPagination<ProductResponse>>
}