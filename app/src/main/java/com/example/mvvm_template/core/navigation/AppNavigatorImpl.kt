package com.example.mvvm_template.core.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.mvvm_template.ui.component.login.GenerateOtpActivity
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.signup.ContinueCompleteActivity
import com.example.mvvm_template.ui.component.verfication.LogiWithOtpActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(val fragmentActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screen, bundle: Bundle?) {
        val intent = when (screen) {
            Screen.GENERATE_OTP -> GenerateOtpActivity.getIntent(fragmentActivity.applicationContext)
            Screen.VERIFY_CODE -> LogiWithOtpActivity.getIntent(fragmentActivity.applicationContext)
            Screen.HOME -> MainActivity.getIntent(fragmentActivity.applicationContext)
            Screen.COMPLETE_REGISTRATION->ContinueCompleteActivity.getIntent(fragmentActivity.applicationContext)
        }
        bundle?.let {
            intent.putExtras(bundle)
        }
        fragmentActivity.startActivity(intent)
    }


}