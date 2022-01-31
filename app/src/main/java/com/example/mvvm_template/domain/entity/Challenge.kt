package com.example.mvvm_template.domain.entity

import android.os.Parcelable
import com.example.mvvm_template.utils.DateAndTimeFormateUtil
import kotlinx.android.parcel.Parcelize
import java.util.*


data class Challenge(
    val description: String? = null,
    val endDate: String? = null,
    val id: Int? = null,
    val imagePath: String? = null,
    val instructions: String? = null,
    val name: String? = null,
    val players: List<Player>? = null,
    val playersCount: Int? = null,
    val rewards: String? = null,
    val startDate: String? = null,
    val status: Int? = null,
    val winner: Winner? = null,
) {
    fun getDays(): Long? {
        val date=DateAndTimeFormateUtil.getDate(startDate,DateAndTimeFormateUtil.ISO_FORMATE)
        if (date!=null){
            val diff=date.time-Date().time
            return (diff)/(24 * 60 * 60 * 1000)
        }
        return null
    }
    fun getHour(): Long? {
        val date=DateAndTimeFormateUtil.getDate(startDate,DateAndTimeFormateUtil.ISO_FORMATE)
        if (date!=null){
            val diff=date.time-Date().time
            return (diff)/(60 * 60 * 1000)
        }
        return null
    }
    fun getMin(): Long? {
        val date=DateAndTimeFormateUtil.getDate(startDate,DateAndTimeFormateUtil.ISO_FORMATE)
        if (date!=null){
            val diff=date.time-Date().time
            return diff/(60 * 1000)
        }
        return null
    }
}

data class Winner(
    val id: Int? = null,
    val imagePath: String? = null,
    val name: String? = null
)

@Parcelize
data class Player(
    val customer: Customer? = null,
    val playerId: String? = null,
    val playerName: String? = null
) : Parcelable

@Parcelize
data class Customer(
    val id: Int? = null,
    val imagePath: String? = null,
    val name: String? = null
) : Parcelable