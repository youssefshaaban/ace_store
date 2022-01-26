package com.example.mvvm_template.data.remote_service.response

import android.os.Parcelable
import com.example.mvvm_template.data.remote_service.response.customer.MemberTypeResponse
import com.example.mvvm_template.data.remote_service.response.customer.PointResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    val email: String,
    val id: String,
    val imagePath: String?=null,
    val imageName: String?=null,
    val mobileNumber: String,
    val name: String,
    val firstName:String?=null,
    val lastName:String?=null,
    val sendCardCodeByEmail:Boolean,
    val sendCardCodeByMobileNumber:Boolean,
    val memberType:MemberTypeResponse,
    val loyalityPoints:PointResponse?=null,
):Parcelable