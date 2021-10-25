package com.example.mvvm_template.data.remote_service.response

import android.os.Parcelable
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
    val lastName:String?=null
):Parcelable