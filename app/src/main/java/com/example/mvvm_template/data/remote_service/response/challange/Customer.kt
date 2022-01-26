package com.example.mvvm_template.data.remote_service.response.challange

data class Customer(
    val id: Int,
    val imagePath: String,
    val name: String
)

fun Customer.toCustomerEntity(): com.example.mvvm_template.domain.entity.Customer =
    com.example.mvvm_template.domain.entity.Customer(this.id, this.imagePath, this.name)