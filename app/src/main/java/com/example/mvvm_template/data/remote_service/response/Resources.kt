package com.example.mvvm_template.data.remote_service.response

import android.os.Parcelable
import com.example.mvvm_template.domain.entity.Resource
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resources(
    val key: String,
    val value: String,
    val valueType: Int
):Parcelable


fun Resources.toResource():Resource = Resource(this.key,this.value,this.valueType)