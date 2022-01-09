package com.example.mvvm_template.ui.component.main.championships

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityChampionshipsBinding
import com.example.mvvm_template.ui.component.main.points.PointsActivity
import com.example.mvvm_template.utils.configRecycle

class ChampionshipsActivity : BaseActivity<ActivityChampionshipsBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=getString(R.string.menu_championships)
        getViewDataBinding().rvChampionships.configRecycle(true)
        stupTaps()
    }

    private fun stupTaps() {
        getViewDataBinding().tabs.addTab(getViewDataBinding().tabs.newTab().setText(getString(R.string.live)))
        getViewDataBinding().tabs.addTab(getViewDataBinding().tabs.newTab().setText(getString(R.string.next_text)))
        getViewDataBinding().tabs.addTab(getViewDataBinding().tabs.newTab().setText(getString(R.string.previous_text)))
        getViewDataBinding().tabs.addTab(getViewDataBinding().tabs.newTab().setText(getString(R.string.all_text)))
    }

    override fun getLayoutId(): Int {
       return  R.layout.activity_championships
    }
    companion object{
        fun getIntent(context: Context): Intent = Intent(context, ChampionshipsActivity::class.java)
    }
}