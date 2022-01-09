package com.example.mvvm_template.core.di

import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.data.local.AppPrefrancesImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
abstract class SharedPreferencesModule {
    @Binds
    abstract fun bindAppprefrances(appPrefrancesImp: AppPrefrancesImp): AppPrefrances
}