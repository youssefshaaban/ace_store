package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.BaseReponseArrayPagination
import com.example.mvvm_template.data.remote_service.response.ReponseOTP
import com.example.mvvm_template.data.remote_service.response.category.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {
    @GET("Categories/Tree")
    suspend fun getCategoriesWithChildren(): Response<BaseReponseArray<CategoryResponse>>
    @GET("Categories/Paged")
    suspend fun getCategories(@Query("PageIndex")PageIndex:Int, @Query("SubCategories")SubCategories:Boolean=true, @Query("PageSize")PageSize:Int=20): Response<BaseReponseArrayPagination<CategoryResponse>>
}