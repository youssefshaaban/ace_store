package com.example.mvvm_template.data.remote_service.response.challange

import com.example.mvvm_template.domain.entity.Challenge

data class ChallengeDetailResponse(
    val description: String,
    val endDate: String,
    val id: Int,
    val imagePath: String,
    val instructions: String,
    val name: String,
    val players: List<Player>?=null,
    val playersCount: Int,
    val rewards: String,
    val startDate: String,
    val status: Int,
    val winner: Winner?=null
)

fun ChallengeDetailResponse.toChallengeEntity(): Challenge =
    Challenge(
        this.description,
        this.endDate,
        this.id,
        this.imagePath,
        this.instructions,
        this.name,
        this.players?.map { t -> t.toPlayerEntity() },
        this.playersCount,
        this.rewards,
        this.startDate,
        this.status,
        this.winner?.toWinnerModel()
    )