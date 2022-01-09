package com.example.mvvm_template.domain.dto

data class RequestAddCart(
    val productId: Int?,
    val quantity: Int?,
    val playerId: String? = null
)