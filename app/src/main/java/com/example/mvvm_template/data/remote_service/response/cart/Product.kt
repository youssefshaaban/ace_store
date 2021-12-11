package com.example.mvvm_template.data.remote_service.response.cart

import com.example.mvvm_template.data.remote_service.response.product.Currency
import com.example.mvvm_template.data.remote_service.response.product.toCurrencyModel

data class Product(
    val currency: Currency,
    val discountValue: Double,
    val imagePath: String,
    val name: String,
    val price: Double,
    val priceAfterDiscount: Double,
    val priceAfterDiscountTaxExcl: Double,
    val priceAfterDiscountTaxIncl: Double,
    val priceTaxExcl: Double,
    val priceTaxIncl: Double,
    val productId: Int,
    val quantity: Int,
    val reductionAmount: Double,
    val reductionAmountTaxExcl: Double,
    val reductionAmountTaxIncl: Double,
    val reductionPercent: Double,
    val taxRate: Double,
    val taxValue: Double,
    val totalPrice: Double,
    val totalPriceAfterDiscount: Double,
    val totalPriceAfterDiscountTaxExcl: Double,
    val totalPriceAfterDiscountTaxIncl: Double,
    val totalPriceTaxExcl: Double,
    val totalPriceTaxIncl: Double,
    val totalTaxValue: Double
)

fun Product.toProduct(): com.example.mvvm_template.domain.entity.Product {
    return com.example.mvvm_template.domain.entity.Product(
        currency = this.currency.toCurrencyModel(),
        descriptionShort = "",
        imagePath = this.imagePath,
        id = this.productId,
        price = this.price,
        name = this.name,
        metaDescription = "",
        quantity = this.quantity,
        priceAfterDiscount = this.priceAfterDiscount
    )
}