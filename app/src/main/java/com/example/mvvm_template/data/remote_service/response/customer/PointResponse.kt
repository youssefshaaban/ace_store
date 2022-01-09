package com.example.mvvm_template.data.remote_service.response.customer

import com.example.mvvm_template.domain.entity.Point

data class PointResponse(
    val equivalentAmount: Double,
    val replacedPoints: Int,
    val totalPoints: Int,
    val transactions: List<TransactionPoints>
)

fun PointResponse.toPoint():Point=Point(this.equivalentAmount,this.replacedPoints,this.totalPoints,this.transactions)