package com.example.mvvm_template.core.di

import android.content.Context
import com.example.mvvm_template.App
import com.example.mvvm_template.data.ServiceGenerator
import com.example.mvvm_template.data.remote_service.api.AccountApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun createAccountApi(servisGenerator: ServiceGenerator): AccountApiService =
        servisGenerator.createService(AccountApiService::class.java)

    @Provides
    @Singleton
    fun createServiceGenerator() = ServiceGenerator()

    @Provides
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return App.context
    }

}