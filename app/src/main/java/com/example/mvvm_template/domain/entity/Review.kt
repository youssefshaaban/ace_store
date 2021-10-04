package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    val customerName: String?=null,
    val customerReview: String?=null,
    val dateAdd: String?=null,
    val grade: Int?=null,
    val title: String?=null,
    val customerId:Int?=null
) :Parcelable