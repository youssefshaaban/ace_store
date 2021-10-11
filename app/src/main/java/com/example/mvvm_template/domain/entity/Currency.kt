package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency(
    val conversionRate: Int? = null,
    val id: Int,
    val symbol: String? = null
):Parcelable