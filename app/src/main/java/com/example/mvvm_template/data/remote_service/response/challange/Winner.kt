package com.example.mvvm_template.data.remote_service.response.challange

data class Winner(
    val id: Int,
    val imagePath: String,
    val name: String
)

fun Winner.toWinnerModel(): com.example.mvvm_template.domain.entity.Winner =
    com.example.mvvm_template.domain.entity.Winner(this.id, this.imagePath, this.name)