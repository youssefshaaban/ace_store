package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentMethod (
    val id: Int?=null,
    val imagePath: String?=null,
    val name: String?=null
):Parcelable