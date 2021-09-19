package com.example.mvvm_template.ui.component

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mvvm_template.R
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.on_boarding.OnBoardingActivity
import com.example.mvvm_template.utils.SavePrefs
import com.example.mvvm_template.utils.startActivityWithFade

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            val user = SavePrefs(this, User::class.java).load()
            if (user == null) {
                startActivityWithFade(OnBoardingActivity.getIntent(this))
                finishAffinity()
            } else {
                startActivityWithFade(MainActivity.getIntent(this))
                finishAffinity()
            }
            finish()
        }, 2000)
    }
    companion object {
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java)
    }


}