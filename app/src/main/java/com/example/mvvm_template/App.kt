package com.example.mvvm_template

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.utils.SavePrefs
import company.tap.gosellapi.GoSellSDK
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import kotlin.random.Random


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        GoSellSDK.init(this, "", this.packageName)
    }

    companion object {

        lateinit var context: Context

        fun getUser(): User? = SavePrefs(context, User::class.java).load()
    }
}
//
//fun pointsBelong(x1:Int, y1:Int,x2:Int, y2:Int, x3:Int, y3:Int, xp:Int, yp:Int, xq:Int, yq:Int):Int{
//    // A(x1,y1)
//    // B(x2,y2)
//    // C(x3,y3)
//
//    val main = x1 * (y2 - y3) +
//            x2 * (y3 - y1) +
//            x3 * (y1 - y2)
//
//}
//
//fun isTriangel(x1:Int, y1:Int,x2:Int, y2:Int, x3:Int, y3:Int):Boolean{
//    val a = x1 * (y2 - y3) +
//            x2 * (y3 - y1) +
//            x3 * (y1 - y2)
//    return a != 0
//}

