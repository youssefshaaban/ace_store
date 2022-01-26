package com.example.mvvm_template.domain.entity

import com.example.mvvm_template.data.remote_service.response.customer.TransactionPoints

data class Point(
    val equivalentAmount: Double?=null,
    val replacedPoints: Int?=null,
    val totalPoints: Int?=null,
    val transactions: List<TransactionPoints>?=null,
    val gainMethods: List<GainedMethod>?=null
)

data class GainedMethod(val gainMethod:String,val points:Int)