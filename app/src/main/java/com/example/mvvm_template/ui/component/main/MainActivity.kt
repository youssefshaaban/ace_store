package com.example.mvvm_template.ui.component.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityMainBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.ui.component.main.bottom.offer.DialogOfferFragment
import com.example.mvvm_template.ui.component.main.pojo.ActionType
import com.example.mvvm_template.ui.component.main.pojo.MenuItem
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    val list: List<MenuItem> by lazy {
        arrayListOf(
            MenuItem(getString(R.string.menu_status), R.drawable.ic_status_icon,ActionType.Status),
            MenuItem(getString(R.string.menu_support), R.drawable.ic_support,ActionType.Support),
            MenuItem(getString(R.string.menu_about), R.drawable.ic_info,ActionType.Info),
            MenuItem(getString(R.string.menu_replacment), R.drawable.ic_replacment,ActionType.Replacement),
            MenuItem(getString(R.string.menu_rate), R.drawable.ic_rate,ActionType.Rate),
            MenuItem(getString(R.string.menu_question), R.drawable.ic_common_question,ActionType.FAQQuestion),
            MenuItem(getString(R.string.menu_share_app), R.drawable.ic_share_app,ActionType.Share)
        )
    }

    val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        getViewDataBinding().rvItems.configRecycle(true)
        getViewDataBinding().rvItems.adapter=MenuItemAdapter(list,::handleActionType)
        getViewDataBinding().contentLayout.contentMain.drawerIcon.setOnClickListener {
            getViewDataBinding().drawerLayout.openDrawer(GravityCompat.START)
        }
        getViewDataBinding().contentLayout.contentMain.offer.setOnClickListener { DialogOfferFragment.newInstance().show(supportFragmentManager,DialogOfferFragment::class.java.name) }
        getViewDataBinding().contentLayout.contentMain.cart.setOnClickListener { openCart() }
        getViewDataBinding().contentLayout.contentMain.search.setOnClickListener {  openSearch()}
    }

    private fun openSearch() {

    }

    private fun openCart() {

    }

    private fun handleActionType(actionType: ActionType) {
        when(actionType){
            ActionType.Share->{}
            ActionType.FAQQuestion->{}
            ActionType.Replacement->{}
            ActionType.Rate->{}
            ActionType.Info->{}
            ActionType.Status->{}
            ActionType.Support->{}
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun observeViewModel() {
        observe(viewModel.title){
            getViewDataBinding().contentLayout.contentMain.title.text=it
        }
    }

}
