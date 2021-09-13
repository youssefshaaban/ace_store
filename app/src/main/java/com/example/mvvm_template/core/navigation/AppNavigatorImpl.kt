package com.example.mvvm_template.core.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(val fragmentActivity: FragmentActivity) :AppNavigator{
    override fun navigateTo(screen: Screen, bundle: Bundle?) {

    }

}