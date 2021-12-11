package com.example.mvvm_template.core.di

import android.content.Context
import android.provider.Settings
import com.example.mvvm_template.App
import com.example.mvvm_template.BuildConfig
import com.example.mvvm_template.core.common.BASE_URL
import com.example.mvvm_template.data.ServiceGenerator
import com.example.mvvm_template.data.remote_service.api.*

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun createAccountApi(servisGenerator: ServiceGenerator): AccountApiService =
        servisGenerator.createService(AccountApiService::class.java)

    @Singleton
    @Provides
    fun createLookupApi(servisGenerator: ServiceGenerator): LookupApi =
        servisGenerator.createService(LookupApi::class.java)

    @Singleton
    @Provides
    fun createFileUpload(servisGenerator: ServiceGenerator): ApiFiles =
        Retrofit.Builder().baseUrl(BASE_URL.replace("api/",""))
            .client(servisGenerator.okHttpBuilder.build())

            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiFiles::class.java)


    @Singleton
    @Provides
    fun createCategoryApi(servisGenerator: ServiceGenerator): CategoryApi =
        servisGenerator.createService(CategoryApi::class.java)

    @Singleton
    @Provides
    fun createCartApi(servisGenerator: ServiceGenerator): CartApi =
        servisGenerator.createService(CartApi::class.java)

    @Singleton
    @Provides
    fun createResourceApi(servisGenerator: ServiceGenerator): ResourcesApi =
        servisGenerator.createService(ResourcesApi::class.java)

    @Singleton
    @Provides
    fun createProductsApi(servisGenerator: ServiceGenerator): ProductsApi =
        servisGenerator.createService(ProductsApi::class.java)

    @Provides
    @Singleton
    fun createServiceGenerator(@Named("deviceId")deviceId:String) = ServiceGenerator(deviceId)

    @Provides
    @Singleton
    @Named("deviceId")
    fun getDeviceId(@ApplicationContext context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

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