package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import com.example.mvvm_template.data.remote_service.response.customer.TransactionWallet
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet(
    val amount: Double,
    val currency: Currency,
    val transactions: List<TransactionWallet>
):Parcelable