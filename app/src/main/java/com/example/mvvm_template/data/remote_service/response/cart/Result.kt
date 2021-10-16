package com.example.mvvm_template.data.remote_service.response.cart

import com.example.mvvm_template.domain.entity.Cart

data class Result(
    val code: String,
    val discountName: String,
    val discountValue: Int,
    val discountValueTaxExcl: Int,
    val discountValueTaxIncl: Int,
    val products: List<Product>,
    val subTotal: Int,
    val subTotalAfterDiscount: Int,
    val subTotalAfterDiscountTaxExcl: Int,
    val subTotalAfterDiscountTaxIncl: Int,
    val subTotalTaxExcl: Int,
    val subTotalTaxIncl: Int,
    val total: Int
)


fun Result.toCart(): Cart = Cart(code = this.code,
    discountName = this.discountName,
    discountValue = this.discountValue,
    this.subTotal,
    this.total,
    products = this.products.map { pro -> pro.toProduct() })