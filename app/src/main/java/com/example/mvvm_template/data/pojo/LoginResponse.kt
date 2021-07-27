package com.example.mvvm_template.data.pojo

import android.os.Parcel
import android.os.Parcelable

data class LoginResponse(
    val imagePath: String,
    val name: String,
    val refreshToken: String,
    val token: String,
    val tokenExpirationDate: String,
    val userId: String,
    val userType: String ,
    val academyId: Int,
    val operatorId: Int,
    val participantId :Int,
    val isAdmin:Boolean,
    val canManageCourse:Boolean,
    val canManageBlog:Boolean,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
        parcel.writeString(name)
        parcel.writeString(refreshToken)
        parcel.writeString(token)
        parcel.writeString(tokenExpirationDate)
        parcel.writeString(userId)
        parcel.writeString(userType)
        parcel.writeInt(academyId)
        parcel.writeInt(operatorId)
        parcel.writeInt(participantId)
        parcel.writeByte(if (isAdmin) 1 else 0)
        parcel.writeByte(if (canManageCourse) 1 else 0)
        parcel.writeByte(if (canManageBlog) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResponse> {
        override fun createFromParcel(parcel: Parcel): LoginResponse {
            return LoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponse?> {
            return arrayOfNulls(size)
        }
    }
}
