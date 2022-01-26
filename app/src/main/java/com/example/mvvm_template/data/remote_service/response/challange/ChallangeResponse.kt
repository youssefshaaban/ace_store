package com.example.mvvm_template.data.remote_service.response.challange

import com.example.mvvm_template.domain.entity.Challenge

data class ChallangeResponse(
    val endDate: String,
    val id: Int,
    val imagePath: String,
    val name: String,
    val playersCount: Int,
    val startDate: String?=null,
    val status: Int?=null,
    val winner: Winner?=null
)

fun ChallangeResponse.toChallengeEntity(): Challenge =
    Challenge(
        endDate = this.endDate,
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        playersCount = this.playersCount,
        status = this.status,
        startDate = this.startDate,
        winner = this.winner?.toWinnerModel()
    )