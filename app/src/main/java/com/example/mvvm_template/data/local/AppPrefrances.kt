package com.example.mvvm_template.data.local

interface AppPrefrances {
    fun setSkipIntro(skipIntro: Boolean)
    fun isSkipIntro():Boolean
}