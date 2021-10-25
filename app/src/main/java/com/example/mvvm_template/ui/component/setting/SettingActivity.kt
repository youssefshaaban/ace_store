package com.example.mvvm_template.ui.component.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivitySettingBinding


class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.account_setting)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }
}