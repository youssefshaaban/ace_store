package com.example.mvvm_template.ui.component.card_categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityCardCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCategoriesActivity : BaseActivity<ActivityCardCategoriesBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=intent.extras?.getString("title")
        supportFragmentManager.beginTransaction().replace(R.id.content,ProductCategoriesFragment().apply {
            arguments=Bundle().apply {
                putBoolean("isSearch",false)
                putInt("cat_id",intent.extras?.getInt("cat_id")!!)
            }
        },ProductCategoriesFragment::class.java.name).commit()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_card_categories
    }

    companion object{
        fun getIntent(context: Context):Intent= Intent(context,ProductCategoriesActivity::class.java)
    }
}