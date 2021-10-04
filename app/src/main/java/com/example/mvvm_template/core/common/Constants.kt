package com.example.mvvm_template.core.common

import android.content.Context
import android.provider.Settings


const val SPLASH_DELAY = 3000
const val BASE_URL = "https://aceustore.apiserver.co/api/"

fun getDeviceId(context: Context): String? {
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )



}
