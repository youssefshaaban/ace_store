package com.example.mvvm_template.data.remote_service.response.customer

import android.os.Parcelable
import com.example.mvvm_template.data.remote_service.response.product.Currency
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionWallet(
    val action: String,
    val actionDate: String,
    val amount: Double,
    val currency: Currency,
    val transactionType: Int
):Parcelable