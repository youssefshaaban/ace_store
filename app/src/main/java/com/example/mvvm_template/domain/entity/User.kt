package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val accountStatus: Int?=null,
    val imagePath: String?=null,
    var name: String?=null,
    val refreshToken: String?=null,
    val token: String?=null,
    val tokenExpirationDate: String?=null,
    val userId: String
):Parcelable

enum class AccountStatus(val status:Int){
    PendingConfirmMobile(0),
    PendingCompleteRegistration(1),
    CompletedRegistration(2),
}