package com.example.mvvm_template.domain.entity

data class Cart(
    val code: String?=null,
    val discountName: String?=null,
    val discountValue: Int?=null,
    val subTotal: Int?=null,
    val total: Int?=null,
    val products: List<Product>
)