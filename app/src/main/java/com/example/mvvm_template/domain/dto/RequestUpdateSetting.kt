package com.example.mvvm_template.domain.dto

data class RequestUpdateSetting(
    val sendCardCodeByEmail: Boolean?=null,
    val sendCardCodeByMobileNumber: Boolean?=null
)