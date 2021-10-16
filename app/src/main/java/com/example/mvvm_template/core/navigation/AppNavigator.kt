package com.example.mvvm_template.core.navigation

import android.os.Bundle

interface AppNavigator {
    fun navigateTo(screen:Screen,bundle: Bundle?)
}

enum class Screen{
    GENERATE_OTP,
    VERIFY_CODE,
    COMPLETE_REGISTRATION,
    HOME,
    PRODUCT_BY_CATEGORY,
    PRODUCT_DETAIL,
    CART
}