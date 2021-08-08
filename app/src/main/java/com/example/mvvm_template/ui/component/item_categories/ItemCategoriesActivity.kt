package com.example.mvvm_template.ui.component.item_categories

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityItemCategoriesBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.utils.configGridRecycle
import com.example.mvvm_template.utils.configRecycle
import dagger.hilt.android.AndroidEntryPoint


class ItemCategoriesActivity : BaseActivity<ActivityItemCategoriesBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2,true)
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        onClickListner()
    }

    private fun onClickListner() {
        getViewDataBinding().list.setOnClickListener {
            getViewDataBinding().list.setColorFilter(R.color.colorAccent)
            getViewDataBinding().grid.setColorFilter(Color.parseColor("#bcbcbc"))
            getViewDataBinding().content.emptyRecycle.configRecycle(true)
        }
        getViewDataBinding().grid.setOnClickListener {
            getViewDataBinding().grid.setColorFilter(R.color.colorAccent)
            getViewDataBinding().list.setColorFilter(Color.parseColor("#bcbcbc"))
            getViewDataBinding().content.emptyRecycle.configGridRecycle(2,true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_item_categories
    }

    override fun observeViewModel() {

    }
}