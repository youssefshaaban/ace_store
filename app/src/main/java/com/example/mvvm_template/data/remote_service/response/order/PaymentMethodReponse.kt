package com.example.mvvm_template.data.remote_service.response.order

import com.example.mvvm_template.data.remote_service.response.product.Currency
import com.example.mvvm_template.domain.entity.PaymentMethod

data class PaymentReponse(
    val result:List<PaymentMethodReponse>?,
    val totalWalletAmount: Double,
    val equivalentPointsAmount: Double,
    val totalPoints: Int?=null,
    val currency: Currency?=null
)

data class PaymentMethodReponse(val id: Int,
                                    val imagePath: String,
                                    val name: String)
fun PaymentMethodReponse.toPaymentMethod():PaymentMethod=PaymentMethod(this.id,this.imagePath,this.name)