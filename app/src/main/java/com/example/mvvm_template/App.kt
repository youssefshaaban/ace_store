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
        GoSellSDK.init(this,"",this.packageName)
    }

    companion object {

        lateinit var context: Context

        fun getUser(): User? = SavePrefs(context, User::class.java).load()
    }
}




//fun solution(A: IntArray): Int {
//    val a=A.filter { x->x>0 }
//    a.distinct().sorted()
//    print(a)
//    var smalest=1
//    for ( i in 0..a.size){
//        if (a[i]==smalest){
//            smalest+=1
//        }
//    }
//    return smalest
//}


fun solution(A: IntArray): Int {
    // write your code in Kotlin 1.3.11 (Linux)
    var numberOfDigitLesstThanone=0
    for (i in A.indices){
        if (A[i]==0)
            return 0
        else if (A[i]<0){
            numberOfDigitLesstThanone++
        }
    }
    return when {
        numberOfDigitLesstThanone%2==0 -> 1
        else -> -1
    }
}
fun main(){
   // IntArray()
    solution(intArrayOf(1, 2, 3, 4, 1))
}