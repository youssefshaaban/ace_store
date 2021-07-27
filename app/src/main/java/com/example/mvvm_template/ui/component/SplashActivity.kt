package com.example.mvvm_template.ui.component

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mvvm_template.R
import com.example.mvvm_template.utils.startActivityWithFade
import com.smart_zone.mnasati.ui.common.intro.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            val user = SavePrefs(this, LoginResponse::class.java).load()
            if (user == null) {
                startActivityWithFade(OnBoardingActivity.getIntent(this))
            } else {

            }
            finish()
        }, 2000)
    }
    companion object {
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java)
    }


}