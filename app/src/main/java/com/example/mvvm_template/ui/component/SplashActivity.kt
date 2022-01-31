package com.example.mvvm_template.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_template.R
import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.ui.component.main.MainActivity
import com.example.mvvm_template.ui.component.on_boarding.OnBoardingActivity
import com.example.mvvm_template.utils.startActivityWithFade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var appPrefrances: AppPrefrances
    var gifDrawable: GifDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupAnimation()
    }

    private fun setupAnimation() {
        try {
            gifDrawable = GifDrawable(resources, R.drawable.splash_gif)
            gifDrawable?.addAnimationListener { loopNumber: Int ->
                gifDrawable?.stop()
                if (!appPrefrances.isSkipIntro()) {
                    startActivityWithFade(OnBoardingActivity.getIntent(this))
                    finishAffinity()
                } else {
                    startActivityWithFade(MainActivity.getIntent(this))
                    finishAffinity()
                }
                finish()
            }
            iv_gif.setImageDrawable(gifDrawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java)
    }

    override fun onDestroy() {
        gifDrawable=null
        super.onDestroy()
    }

}