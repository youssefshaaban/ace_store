package com.example.mvvm_template.data.remote_service.response.customer

import android.os.Parcelable
import com.example.mvvm_template.domain.entity.GainedMethod
import com.example.mvvm_template.domain.entity.Point
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PointResponse(
    val equivalentAmount: Double,
    val replacedPoints: Int,
    val totalPoints: Int,
    val transactions: List<TransactionPoints>?=null,
    val  gainMethods: List<GainedMethodsResponse>?=null
):Parcelable


@Parcelize
data class GainedMethodsResponse(
    val gainMethod: String,
    val points: Int
):Parcelable

fun GainedMethodsResponse.toGainedMethod():GainedMethod= GainedMethod(this.gainMethod,this.points)

fun PointResponse.toPoint():Point=Point(this.equivalentAmount,this.replacedPoints,this.totalPoints,this?.transactions,gainMethods?.map { t->t.toGainedMethod() })