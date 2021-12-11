package com.example.mvvm_template.domain.dto

import java.io.Serializable

data class RequestPaymentApi(
    val language:String ?= null,
    val access_code:String ?= null,
    val device_id:String ?=null,
    val service_command:String ?= "SDK_TOKEN",
    val signature:String ?= null,
): Serializable
