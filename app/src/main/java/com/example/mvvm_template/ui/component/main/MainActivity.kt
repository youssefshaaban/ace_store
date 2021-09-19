package com.example.mvvm_template.ui.component.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityMainBinding
import com.example.mvvm_template.domain.entity.Profile

import com.example.mvvm_template.ui.component.main.bottom.offer.DialogOfferFragment
import com.example.mvvm_template.ui.component.main.pojo.ActionType
import com.example.mvvm_template.ui.component.main.pojo.MenuItem
import com.example.mvvm_template.ui.component.main.rate_app.RateMeFragment
import com.example.mvvm_template.ui.component.main.web_view.WebViewActivity
import com.example.mvvm_template.ui.component.search.SearchActivity
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.startActivityWithFade
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewModel:MainViewModel by viewModels()
    val list: List<MenuItem> by lazy {
        getListMenu()
    }
    @Inject
    lateinit var navigator: AppNavigator
    private fun getListMenu():List<MenuItem> {
        return arrayListOf(
            MenuItem(getString(R.string.menu_status), R.drawable.ic_status_icon, ActionType.Status),
            MenuItem(getString(R.string.menu_support), R.drawable.ic_support, ActionType.Support),
            MenuItem(getString(R.string.menu_about), R.drawable.ic_info, ActionType.Info),
            MenuItem(
                getString(R.string.menu_replacment),
                R.drawable.ic_replacment,
                ActionType.Replacement
            ),
            MenuItem(getString(R.string.menu_rate), R.drawable.ic_rate, ActionType.Rate),
            MenuItem(
                getString(R.string.menu_question),
                R.drawable.ic_common_question,
                ActionType.FAQQuestion
            ),
            MenuItem(getString(R.string.menu_share_app), R.drawable.ic_share_app, ActionType.Share)
        )
    }

    private fun handelDataStatVerifyOTP(dataState: DataState<Profile>) {
        when (dataState) {
            is DataState.Success -> {
                getViewDataBinding().navView.name.text=dataState.data.name
                getViewDataBinding().navView.imageView.loadImage(dataState.data.imagePath,R.drawable.bg_no_image)
                getViewDataBinding().navView.phone.text=dataState.data.mobileNumber
            }
            is DataState.Error -> {
                handleFaluir(dataState.error)
            }
        }
    }
    private fun handelDataStateLogOut(dataState: DataState<Boolean>) {
        when (dataState) {
            is DataState.Loading->showLoading()
            is DataState.Success -> {
                hideLoading()
                navigator.navigateTo(Screen.GENERATE_OTP,null)
                finish()
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        getViewDataBinding().rvItems.configRecycle(true)
        getViewDataBinding().rvItems.adapter = MenuItemAdapter(list, ::handleActionType)
        getViewDataBinding().contentLayout.contentMain.drawerIcon.setOnClickListener {
            getViewDataBinding().drawerLayout.openDrawer(GravityCompat.START)
        }
        getViewDataBinding().contentLayout.contentMain.offer.setOnClickListener {
            DialogOfferFragment.newInstance().show(
                supportFragmentManager,
                DialogOfferFragment::class.java.name
            )
        }
        getViewDataBinding().contentLayout.contentMain.cart.setOnClickListener { openCart() }
        getViewDataBinding().contentLayout.contentMain.search.setOnClickListener { openSearch() }
        observeViewModels()
        checkUser()
    }

    private fun checkUser() {
        if (App.getUser()!=null){
            viewModel.getProfile()
            getViewDataBinding().txt.text=getString(R.string.logout)
        }else{
            getViewDataBinding().txt.text=getString(R.string.login)
        }
        getViewDataBinding().txt.setOnClickListener { perforClickActionLogin() }
    }

    private fun perforClickActionLogin(){
        if (App.getUser()!=null){
            viewModel.logOut()
        }else{
           navigator.navigateTo(Screen.GENERATE_OTP,null)
           finish()
        }
    }

    private fun observeViewModels() {
        viewModel.title.observe(this){
            getViewDataBinding().contentLayout.contentMain.title.text = it
        }
        observe(viewModel.observProfile,::handelDataStatVerifyOTP)
        observe(viewModel.observeSuccess,::handelDataStateLogOut)
    }

    private fun openSearch() {
        startActivityWithFade(SearchActivity.getIntent(this))
    }

    private fun openCart() {

    }

    private fun handleActionType(actionType: ActionType) {
        when (actionType) {
            ActionType.Share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            ActionType.FAQQuestion -> {
            }
            ActionType.Replacement -> {
                startActivityWithFade(
                    WebViewActivity.getIntent(this).putExtra(
                        "title",
                        getString(R.string.menu_replacment)
                    ).putExtra("url","")
                )
            }
            ActionType.Rate -> {
                //startActivityWithFade(RateMeFragment.getIntent(this))
            }
            ActionType.Info -> {
//                startActivityWithFade(
//                    WebViewActivity.getIntent(this).putExtra(
//                        "title",
//                        getString(R.string.menu_about)
//                    ).putExtra("url","")
//                )
            }
            ActionType.Status -> {

            }
            ActionType.Support -> {
                val contact = "+00 9876543210" // use country code with your phone number

                val url = "https://api.whatsapp.com/send?phone=$contact"
                try {
                    val pm: PackageManager = getPackageManager()
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: PackageManager.NameNotFoundException) {
                    Toast.makeText(
                        this,
                        "Whatsapp app not installed in your phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
