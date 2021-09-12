package com.example.mvvm_template.data.remote_service.request

data class RequestLogin(
    val grantType: String,
    val otp: String,
    val password: String,
    val refreshToken: String,
    val userName: String
)