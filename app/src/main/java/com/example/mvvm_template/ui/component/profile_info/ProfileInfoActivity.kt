package com.example.mvvm_template.ui.component.profile_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityProfileInfoBinding



class ProfileInfoActivity : BaseActivity<ActivityProfileInfoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().cancel.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.account_info)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_profile_info
    }

}