package com.example.mvvm_template.data.remote_service.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    val email: String,
    val id: String,
    val imagePath: String,
    val mobileNumber: String,
    val name: String
):Parcelable