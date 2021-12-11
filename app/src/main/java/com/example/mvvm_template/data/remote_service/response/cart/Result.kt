package com.example.mvvm_template.data.remote_service.response.cart

import com.example.mvvm_template.domain.entity.Cart

data class Result(
    val code: String,
    val discountName: String,
    val discountValue: Double,
    val discountValueTaxExcl: Double,
    val discountValueTaxIncl: Double,
    val products: List<Product>,
    val subTotal: Double,
    val subTotalAfterDiscount: Double,
    val subTotalAfterDiscountTaxExcl: Double,
    val subTotalAfterDiscountTaxIncl: Double,
    val subTotalTaxExcl: Double,
    val subTotalTaxIncl: Double,
    val total: Double
)


fun Result.toCart(): Cart = Cart(code = this.code,
    discountName = this.discountName,
    discountValue = this.discountValue,
    this.subTotal,
    this.total,
    products = this.products.map { pro -> pro.toProduct() })