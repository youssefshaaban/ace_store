package com.example.mvvm_template.data.remote_service.response.order

data class OrderDetail(
    val imagePath: String?=null,
    val productName: String?=null,
    val quantity: Int?=null,
    val totalPrice: Double?=null,
    val codes:List<String>
)

fun OrderDetail.toOrderDetailEntity():com.example.mvvm_template.domain.entity.OrderDetail=com.example.mvvm_template.domain.entity.OrderDetail(
   imagePath =  this.imagePath, productName = this.productName, quantity = this.quantity, totalPrice = this.totalPrice,codes=this.codes

)