package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderDetail(
    val imagePath: String?=null,
    val productName: String?=null,
    val quantity: Int?=null,
    val totalPrice: Double?=null
):Parcelable