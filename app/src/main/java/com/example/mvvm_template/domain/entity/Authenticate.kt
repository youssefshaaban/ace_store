package com.example.mvvm_template.domain.entity

data class Authenticate(
    val accountStatus: Int,
    val imagePath: String,
    val isCompleteRegistration: Boolean,
    val name: String,
    val refreshToken: String,
    val token: String,
    val tokenExpirationDate: String,
    val userId: String
)