package com.example.mvvm_template.data.remote_service.response.challange

data class Player(
    val customer: Customer?=null,
    val playerId: String,
    val playerName: String
)

fun Player.toPlayerEntity():com.example.mvvm_template.domain.entity.Player=com.example.mvvm_template.domain.entity.Player(this.customer?.toCustomerEntity(),this.playerId,this.playerName)