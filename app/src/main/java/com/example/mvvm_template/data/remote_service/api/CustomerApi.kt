package com.example.mvvm_template.data.remote_service.api

import com.example.mvvm_template.data.remote_service.response.BaseReponse
import com.example.mvvm_template.data.remote_service.response.BaseReponseArray
import com.example.mvvm_template.data.remote_service.response.customer.MemberTypeResponse
import com.example.mvvm_template.data.remote_service.response.customer.PointResponse
import com.example.mvvm_template.data.remote_service.response.customer.WalletResponse
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CustomerApi {

    @GET("Customer/Points")
    suspend fun getPoints(): Response<BaseReponse<PointResponse>>
    @GET("MemberTypes")
    suspend fun getMemberType(): Response<BaseReponseArray<MemberTypeResponse>>
    @GET("Customer/Wallet")
    suspend fun getWallet():Response<BaseReponse<WalletResponse?>>
    @POST("Customer/Wallet/Charge")
    suspend fun chargeWallet(@Body requestChargeWallet: RequestChargeWallet):Response<BaseReponse<WalletResponse?>>
}