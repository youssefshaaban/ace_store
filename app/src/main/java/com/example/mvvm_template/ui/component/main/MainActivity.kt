package com.example.mvvm_template.ui.component.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityMainBinding
import com.example.mvvm_template.ui.base.BaseActivity
import com.example.mvvm_template.ui.component.main.bottom.offer.DialogOfferFragment
import com.example.mvvm_template.ui.component.main.pojo.ActionType
import com.example.mvvm_template.ui.component.main.pojo.MenuItem
import com.example.mvvm_template.ui.component.main.rate_app.RateMeActivity
import com.example.mvvm_template.ui.component.main.web_view.WebViewActivity
import com.example.mvvm_template.ui.component.search.SearchActivity
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.startActivityWithFade
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {


    val list: List<MenuItem> by lazy {
        arrayListOf(
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

    val viewModel: MainViewModel by viewModel()
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
                startActivityWithFade(RateMeActivity.getIntent(this))
            }
            ActionType.Info -> {
                startActivityWithFade(
                    WebViewActivity.getIntent(this).putExtra(
                        "title",
                        getString(R.string.menu_about)
                    ).putExtra("url","")
                )
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

    override fun observeViewModel() {
        observe(viewModel.title) {
            getViewDataBinding().contentLayout.contentMain.title.text = it
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
