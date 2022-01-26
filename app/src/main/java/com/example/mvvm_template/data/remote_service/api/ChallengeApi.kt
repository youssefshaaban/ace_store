package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.BaseReponseArrayPagination
import com.example.mvvm_template.data.remote_service.response.category.CategoryResponse
import com.example.mvvm_template.data.remote_service.response.challange.ChallangeResponse
import com.example.mvvm_template.data.remote_service.response.challange.ChallengeDetailResponse
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import retrofit2.Response
import retrofit2.http.*

interface ChallengeApi {
    @GET("Challenges")
    suspend fun getChallengesById(@Query("Id")id:Int): Response<BaseReponse<ChallengeDetailResponse>>
    @POST("Challenges/Join")
    suspend fun joinChallenge(@Body requestChallengeDTo: RequestChallengeDTo): Response<BaseReponse<Boolean>>
    @GET("Challenges/Paged")
    suspend fun getChallenges(@QueryMap map:Map<String,String>): Response<BaseReponseArrayPagination<ChallangeResponse>>
}