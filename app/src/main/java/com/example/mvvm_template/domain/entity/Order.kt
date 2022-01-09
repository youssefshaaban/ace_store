package com.example.mvvm_template.domain.entity

data class Order(
    val currentState: String? = null,
    val discountValue: Double? = null,
    val id: Int? = null,
    val imagePath: String? = null,
    val orderDate: String? = null,
    val orderDetails: List<OrderDetail>? = null,
    val reference: String? = null,
    val tax: Double? = null,
    val orderStateId:Int?=null,
    val totalPrice: Double? = null,
    val totalPriceBeforDiscount: Double? = null
)