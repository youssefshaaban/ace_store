package com.example.mvvm_template.core.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.mvvm_template.ui.component.card_categories.ProductCategoriesActivity
import com.example.mvvm_template.ui.component.cart.CartActivity
import com.example.mvvm_template.ui.component.auth.login.GenerateOtpActivity
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.product_detail.ProductDetailActivity
import com.example.mvvm_template.ui.component.main.bottom.profile.profile_info.ProfileInfoActivity
import com.example.mvvm_template.ui.component.auth.signup.ContinueCompleteActivity
import com.example.mvvm_template.ui.component.auth.verfication.LogiWithOtpActivity
import com.example.mvvm_template.ui.component.setting.SettingActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val fragmentActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screen, bundle: Bundle?) {
        val intent = when (screen) {
            Screen.GENERATE_OTP -> GenerateOtpActivity.getIntent(fragmentActivity.applicationContext).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            Screen.VERIFY_CODE -> LogiWithOtpActivity.getIntent(fragmentActivity.applicationContext)
            Screen.HOME -> MainActivity.getIntent(fragmentActivity.applicationContext).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            Screen.COMPLETE_REGISTRATION->ContinueCompleteActivity.getIntent(fragmentActivity.applicationContext)
            Screen.PRODUCT_BY_CATEGORY->ProductCategoriesActivity.getIntent(fragmentActivity.applicationContext)
            Screen.PRODUCT_DETAIL-> Intent(fragmentActivity.applicationContext,ProductDetailActivity::class.java)
            Screen.CART-> Intent(fragmentActivity.applicationContext,CartActivity::class.java)
            Screen.PROFILE_SCREEN-> Intent(fragmentActivity.applicationContext,ProfileInfoActivity::class.java)
            Screen.ACCOUNT_SETTING->Intent(fragmentActivity.applicationContext,SettingActivity::class.java)
        }
        bundle?.let {
            intent.putExtras(bundle)
        }
        fragmentActivity.startActivity(intent)
    }


}