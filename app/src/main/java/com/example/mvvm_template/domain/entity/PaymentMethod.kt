package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentMethod (
    val id: Int?=null,
    val imagePath: String?=null,
    val name: String?=null,
    var equivalentPointsAmount:Double ?=null,
    var totalWalletAmount:Double ?=null,
    var totalPoint:Int?=null,
    var currency: Currency?=null
):Parcelable


val WALLET_PAYMENT_METHOD_TYPE=5
val POINTS_PAYMENT_METHOD_TYPE=6