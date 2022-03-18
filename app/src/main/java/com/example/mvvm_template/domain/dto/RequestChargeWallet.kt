package com.example.mvvm_template.domain.dto

data class RequestChargeWallet(
    val amount: Double?=null,
//    val cryptedData: String?=null,
    val paymentMethodId: Int?=null,
    val merchantReference:Any?=null,
    val fortId:Any?=null
)