package com.example.mvvm_template.domain.dto

data class RequestOrder(
//    val cryptedData: String?=null,
    val paymentMethodId: Int,
    val merchantReference:Any?=null,
    val fortId:Any?=null
)