package com.example.mvvm_template.domain.dto

import com.example.mvvm_template.BuildConfig
import com.payfort.fortpaymentsdk.FortSdk
import java.io.Serializable

data class RequestPaymentApi(
    val language:String ?= null,
    val access_code:String ?= null,
    val device_id:String ?=null,
    val service_command:String ?= "SDK_TOKEN",
    val signature:String ?= null,
    val merchant_identifier:String ?= BuildConfig.MERCHANT_ID,
): Serializable
