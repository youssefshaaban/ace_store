package com.example.mvvm_template.data.remote_service.response

import android.os.Parcelable
import com.example.mvvm_template.domain.entity.Country
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryResponse(
    val countryCode: String,
    val id: Int,
    val imagePath: String,
    val isoCode: String,
    val name: String
):Parcelable


fun CountryResponse.toCountry():Country{
    return Country(id, name, countryCode, imagePath)
}