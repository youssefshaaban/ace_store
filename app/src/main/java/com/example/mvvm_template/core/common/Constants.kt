package com.example.mvvm_template.core.common

import android.content.Context
import android.provider.Settings


const val SPLASH_DELAY = 3000
const val BASE_URL = "https://aceustore.apiserver.co/api/"
const val GO_TO_HISTORY="goToHistory"
const val CHARGE_BY_VISA="ChargeByVisa"
const val BUY_PRODUCT="BuyProduct"
const val CART_UPDATE="cart_update"
const val CART_COUNT="cart_count"
const val LIVE=1
const val NEXT=2
const val PRVIOUS=3
fun getDeviceId(context: Context): String? {
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )



}
