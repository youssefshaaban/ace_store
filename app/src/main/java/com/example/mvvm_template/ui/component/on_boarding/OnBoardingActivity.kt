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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        binding.boardingPager.adapter = OnBoardingAdapter(this)
        TabLayoutMediator(binding.tabLayoutDots, binding.boardingPager, { _, _ -> }).attach()
//        val states = StateListDrawable()
//        states.addState(
//            intArrayOf(android.R.attr.state_selected),GradientDrawable()
//        )
        initClickListner()
    }

    private fun initClickListner() {

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

}
