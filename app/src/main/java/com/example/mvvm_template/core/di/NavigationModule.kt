package com.example.mvvm_template.core.di

import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {
  @Binds
  abstract fun bindAppNavigator(navigator: AppNavigatorImpl): AppNavigator
}