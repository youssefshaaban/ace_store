package com.example.mvvm_template.data.remote_service.response.customer

import com.example.mvvm_template.domain.entity.MemberType

data class MemberTypeResponse(
    val colorCode: String,
    val description: String,
    val id: Int,
    val minimumPoints: Int,
    val name: String
)

fun MemberTypeResponse.toMemberType():MemberType=MemberType(this.colorCode,this.description,this.id,this.minimumPoints,this.name)