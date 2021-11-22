package com.example.mvvm_template.domain.entity

data class AmazonSdk(val signature:String ?= null,
                     val access_code:String ?= null,
                     val merchant_identifier:String ?= null,
                     val sdk_token:String ?= null)