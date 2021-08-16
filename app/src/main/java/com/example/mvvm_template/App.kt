package com.example.mvvm_template

import android.app.Application
import android.content.Context
import com.example.mvvm_template.di.AppInjictors


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        AppInjictors.init(this)
    }
    companion object{
        lateinit var context: Context
    }
}