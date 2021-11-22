package com.example.mvvm_template.domain.dto

data class RequestOrder(
//    val cryptedData: String?=null,
    val paymentMethodId: Int,
    val merchantRef:String?=null,
    val fortId:String?=null
)