package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resource (
    val key: String,
    val value: String,
    val valueType: Int
): Parcelable