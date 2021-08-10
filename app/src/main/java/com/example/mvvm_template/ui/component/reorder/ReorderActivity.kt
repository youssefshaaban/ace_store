package com.example.mvvm_template.ui.component.reorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityReorderBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.utils.configRecycle

class ReorderActivity : BaseActivity<ActivityReorderBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().rvProduct.configRecycle(true)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = getString(R.string.reOrder)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reorder
    }

    override fun observeViewModel() {

    }
}