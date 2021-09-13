package com.example.mvvm_template.domain.dto

data class RequestLogin(
    val grantType: String,
    val otp: String,
    val password: String,
    val refreshToken: String,
    val userName: String
)