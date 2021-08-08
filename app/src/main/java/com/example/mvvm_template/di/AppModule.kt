package com.example.mvvm_template.di

import com.example.mvvm_template.data.ServiceGenerator


class AppModule {

    fun provideServiceGenerator() : ServiceGenerator = ServiceGenerator()

}