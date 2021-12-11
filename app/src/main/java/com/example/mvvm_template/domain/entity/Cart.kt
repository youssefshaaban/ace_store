package com.example.mvvm_template.domain.entity

data class Cart(
    val code: String?=null,
    val discountName: String?=null,
    val discountValue: Double?=null,
    val subTotal: Double?=null,
    val total: Double?=null,
    val products: List<Product>?=null
)