package com.example.mvvm_template

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.utils.SavePrefs
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.StringBuilder


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
        fun getUser(): User? = SavePrefs(context, User::class.java).load()
    }
}
