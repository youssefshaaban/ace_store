package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    val currency: Currency?=null,
    val descriptionShort: String?=null,
    val id: Int,
    val imagePath: String?=null,
    val metaDescription: String?=null,
    val name: String?=null,
    val price: Int?=null,
    val priceAfterDiscount: Int?=null,
    val images:List<String>?=null,
    val reviews:List<Review>?=null
):Parcelable