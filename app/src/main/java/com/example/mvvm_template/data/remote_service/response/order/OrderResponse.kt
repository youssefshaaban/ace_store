package com.example.mvvm_template.data.remote_service.response.order

import com.example.mvvm_template.domain.entity.Order

data class OrderResponse(
    val currentState: String?=null,
    val discountValue: Double?=null,
    val id: Int?=null,
    val imagePath: String?=null,
    val orderDate: String?=null,
    val orderDetails: List<OrderDetail>?=null,
    val reference: String?=null,
    val tax: Double?=null,
    val orderStateId:Int,
    val totalPrice: Double?=null,
    val totalPriceBeforDiscount: Double?=null
)

fun OrderResponse.toOrder():Order=Order(
    currentState = this.currentState,discountValue=this.discountValue, id = this.id, imagePath = this.imagePath, orderDate = this.orderDate,
    tax = this.tax, totalPrice = this.totalPrice, reference = this.reference,
    orderStateId = this.orderStateId,
    orderDetails = this.orderDetails?.map { t->t.toOrderDetailEntity() }, totalPriceBeforDiscount = this.totalPriceBeforDiscount
)