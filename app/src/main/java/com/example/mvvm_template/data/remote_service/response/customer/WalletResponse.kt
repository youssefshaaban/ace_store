package com.example.mvvm_template.data.remote_service.response.customer

import com.example.mvvm_template.data.remote_service.response.product.Currency
import com.example.mvvm_template.data.remote_service.response.product.toCurrencyModel
import com.example.mvvm_template.domain.entity.Wallet

data class WalletResponse(
    val amount: Double,
    val currency: Currency,
    val transactions: List<TransactionWallet>
)

fun WalletResponse.toWallet():Wallet= Wallet(this.amount,this.currency.toCurrencyModel(),transactions)