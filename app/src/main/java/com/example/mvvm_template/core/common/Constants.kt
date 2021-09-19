package com.example.mvvm_template.core.common

import android.content.Context
import android.provider.Settings


const val SPLASH_DELAY = 3000
const val BASE_URL = "http://168.119.97.15:9090/api/"

fun getDeviceId(context: Context): String? {
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )



}