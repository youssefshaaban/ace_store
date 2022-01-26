package com.example.mvvm_template.data.remote_service.response.customer

import android.os.Parcelable
import com.example.mvvm_template.domain.entity.MemberType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberTypeResponse(
    val colorCode: String,
    val description: String,
    val id: Int,
    val minimumPoints: Int,
    val name: String
):Parcelable

fun MemberTypeResponse.toMemberType():MemberType=MemberType(this.colorCode,this.description,this.id,this.minimumPoints,this.name)