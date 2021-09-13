package com.example.mvvm_template.core.navigation

import android.os.Bundle

interface AppNavigator {
    fun navigateTo(screen:Screen,bundle: Bundle?)
}

enum class Screen{
    MAP,
    RESTAURANT
}