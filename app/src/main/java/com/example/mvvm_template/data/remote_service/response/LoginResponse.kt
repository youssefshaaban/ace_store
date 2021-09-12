package com.example.mvvm_template.data.remote_service.response

data class LoginResponse(
    val accountStatus: Int,
    val accountStatusDescription: String,
    val imagePath: String,
    val isCompleteRegistration: Boolean,
    val name: String,
    val refreshToken: String,
    val token: String,
    val tokenExpirationDate: String,
    val userId: String
)