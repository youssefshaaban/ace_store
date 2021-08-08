package com.example.mvvm_template.ui.component.on_boarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityOnBoardingBinding
import com.example.mvvm_template.utils.makeStatusBarTransparent
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingBinding
    val list:List<ModelBoarding> by lazy {
        listOf(ModelBoarding(getString(R.string.title1),getString(R.string.subtitle1),R.drawable.ic_image_first),
            ModelBoarding(getString(R.string.title2),getString(R.string.subtitle2),R.drawable.ic_image_second),
            ModelBoarding(getString(R.string.title3),getString(R.string.subtitle3),R.drawable.ic_image_third)
            )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        binding.boardingPager.adapter = OnBoardingAdapter(this,list)
        TabLayoutMediator(binding.tabLayoutDots, binding.boardingPager, { _, _ -> }).attach()
        initClickListner()
    }

    private fun initClickListner() {
        binding.skip.setOnClickListener { goToHome() }
        binding.login.setOnClickListener {  goToLogin() }
    }

    private fun goToHome() {

    }

    private fun goToLogin() {

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

}
