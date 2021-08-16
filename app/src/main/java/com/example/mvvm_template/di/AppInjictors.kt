package com.example.mvvm_template.di

import com.example.mvvm_template.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
object AppInjictors {

    fun init(app: App) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(app)
            modules(listOf(viewModelModule))
        }
    }
}