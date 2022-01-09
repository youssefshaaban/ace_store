package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateOrder(val orderId: Int,val products:List<OrderDetail> ):Parcelable