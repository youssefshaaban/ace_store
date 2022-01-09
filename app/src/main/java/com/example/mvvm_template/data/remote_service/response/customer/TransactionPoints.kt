package com.example.mvvm_template.data.remote_service.response.customer

data class TransactionPoints(
    val gainDate: String,
    val points: Int,
    val pointsGainMethod: String,
    val transactionType: Int
)