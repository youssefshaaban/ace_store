package com.example.mvvm_template.ui.component.card_categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityCardCategoriesBinding

class CardCategoriesActivity : BaseActivity<ActivityCardCategoriesBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        supportFragmentManager.beginTransaction().replace(R.id.content,CardCategoriesFragment().apply {
            arguments=Bundle().apply {
                putBoolean("isSearch",false)
            }
        },CardCategoriesFragment::class.java.name).commit()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_card_categories
    }

}