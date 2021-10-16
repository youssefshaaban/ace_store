package com.example.mvvm_template.data.remote_service.response.cart

import com.example.mvvm_template.data.remote_service.response.product.Currency
import com.example.mvvm_template.data.remote_service.response.product.toCurrencyModel

data class Product(
    val currency: Currency,
    val discountValue: Int,
    val imagePath: String,
    val name: String,
    val price: Int,
    val priceAfterDiscount: Int,
    val priceAfterDiscountTaxExcl: Int,
    val priceAfterDiscountTaxIncl: Int,
    val priceTaxExcl: Int,
    val priceTaxIncl: Int,
    val productId: Int,
    val quantity: Int,
    val reductionAmount: Int,
    val reductionAmountTaxExcl: Int,
    val reductionAmountTaxIncl: Int,
    val reductionPercent: Int,
    val taxRate: Int,
    val taxValue: Int,
    val totalPrice: Int,
    val totalPriceAfterDiscount: Int,
    val totalPriceAfterDiscountTaxExcl: Int,
    val totalPriceAfterDiscountTaxIncl: Int,
    val totalPriceTaxExcl: Int,
    val totalPriceTaxIncl: Int,
    val totalTaxValue: Int
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