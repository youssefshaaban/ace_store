package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.SliderResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiFiles {
    @Multipart
    @POST("File/Upload")
    suspend fun uploadFiles(@Part parts: ArrayList<MultipartBody.Part>): Response<List<String>>

}