package com.example.mvvm_template.data.remote_service.response.customer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionPoints(
    val gainDate: String,
    val points: Int,
    val pointsGainMethod: String,
    val transactionType: Int
):Parcelable