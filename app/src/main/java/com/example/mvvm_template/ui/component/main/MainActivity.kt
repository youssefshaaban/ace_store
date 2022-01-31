package com.example.mvvm_template.ui.component.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm_template.App
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.*
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityMainBinding
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.entity.RateOrder
import com.example.mvvm_template.domain.interactor.account.UpdateFirBaseTokenUseCase
import com.example.mvvm_template.ui.component.auth.logout.LogoutDialog

import com.example.mvvm_template.ui.component.main.bottom.offer.DialogOfferFragment
import com.example.mvvm_template.ui.component.main.championships.ChampionshipsActivity
import com.example.mvvm_template.ui.component.main.member_ship.MemberShipActivity
import com.example.mvvm_template.ui.component.main.points.PointsActivity
import com.example.mvvm_template.ui.component.main.pojo.ActionType
import com.example.mvvm_template.ui.component.main.pojo.MenuItem
import com.example.mvvm_template.ui.component.main.rate_app.RateMeActivity
import com.example.mvvm_template.ui.component.main.wallet.WalletActivity
import com.example.mvvm_template.ui.component.main.web_view.WebViewActivity
import com.example.mvvm_template.ui.component.rate.RateDialogFragment
import com.example.mvvm_template.ui.component.search.SearchActivity
import com.example.mvvm_template.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ms_square.etsyblur.BlurSupport
import company.tap.gosellapi.internal.api.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    @Inject
    lateinit var navigator: AppNavigator

    val viewModel: MainViewModel by viewModels()
    lateinit var navController: NavController
    val list: List<MenuItem> by lazy {
        getListMenu()
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.getIntExtra(CART_COUNT, 0)?.let {
                if (it != 0) {
                    getViewDataBinding().contentLayout.contentMain.number.text = "$it"
                    getViewDataBinding().contentLayout.contentMain.number.toVisible()
                } else {
                    getViewDataBinding().contentLayout.contentMain.number.text = ""
                    getViewDataBinding().contentLayout.contentMain.number.toGone()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    private fun getListMenu(): List<MenuItem> {
        return arrayListOf(
            MenuItem(
                getString(R.string.menu_member_ship),
                R.drawable.ic_member,
                ActionType.MemberShip
            ),
            MenuItem(getString(R.string.menu_status), R.drawable.ic_point, ActionType.Status),
            MenuItem(getString(R.string.menu_wallet), R.drawable.ic_wallet, ActionType.Wallet),
            MenuItem(
                getString(R.string.menu_championships),
                R.drawable.ic_championships,
                ActionType.Championships
            ),
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
                getViewDataBinding().navView.name.text = dataState.data.name
                getViewDataBinding().navView.imageView.loadImage(
                    dataState.data.imagePath,
                    R.drawable.bg_no_image
                )
                getViewDataBinding().navView.phone.text = dataState.data.mobileNumber
                getViewDataBinding().navView.memberType.text = dataState.data.memberType?.name +" ("+dataState.data.point?.totalPoints+")"
                dataState.data.memberType?.colorCode?.let {
                    getViewDataBinding().navView.contentMember.background.setTint(Color.parseColor(it))
                    getViewDataBinding().navView.imageView.borderColor=Color.parseColor(it)
                }

            }
            is DataState.Error -> {
                handleFaluir(dataState.error)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        getViewDataBinding().contentLayout.contentMain.navView.setOnNavigationItemSelectedListener(
            ::handleSectionItem
        )
        BlurSupport.addTo(getViewDataBinding().drawerLayout)
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
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(CART_UPDATE))
        if (App.getUser() != null)
            viewModel.updateFirebaseToken(
                UpdateFirBaseTokenUseCase.RequestUpdateFirbase(
                    getDeviceId(
                        context = this
                    )!!
                )
            )
        checkUser()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.getBooleanExtra(GO_TO_HISTORY, false)) {
            navController.navigate(R.id.purchase)
            getViewDataBinding().contentLayout.contentMain.number.text = ""
            getViewDataBinding().contentLayout.contentMain.number.toGone()
        }
    }

    private fun handleSectionItem(menuItem: android.view.MenuItem): Boolean {
        return if (menuItem.itemId == navController.currentDestination?.id) {
            true
        } else {
            navController.navigate(menuItem.itemId)
            true
        }
    }

    private fun checkUser() {
        if (App.getUser() != null) {
            viewModel.getProfile()
            getViewDataBinding().txt.text = getString(R.string.logout)
            viewModel.getLastUnratedOrder()
        } else {
            getViewDataBinding().txt.text = getString(R.string.login)
        }
        getViewDataBinding().txt.setOnClickListener { perforClickActionLogin() }
    }

    private fun perforClickActionLogin() {
        if (App.getUser() != null) {
            val fragment =
                LogoutDialog()
            fragment.show(supportFragmentManager, LogoutDialog::class.java.name)
        } else {
            navigator.navigateTo(Screen.GENERATE_OTP, null)
            finish()
        }
    }

    private fun observeViewModels() {
        viewModel.title.observe(this) {
            getViewDataBinding().contentLayout.contentMain.title.text = it
        }
        observe(viewModel.observProfile, ::handelDataStatVerifyOTP)
        observe(viewModel.rateOrderData, ::handleRateOrderResult)
        observe(viewModel.carCount) {
            if (it != 0) {
                getViewDataBinding().contentLayout.contentMain.number.toVisible()
                getViewDataBinding().contentLayout.contentMain.number.text = it.toString()
            } else {
                getViewDataBinding().contentLayout.contentMain.number.toGone()
            }
        }
    }

    private fun handleRateOrderResult(dataState: DataState<RateOrder?>) {
        when (dataState) {
            is DataState.Success -> {
                if (dataState.data != null && !dataState.data.products.isNullOrEmpty()) {
                    RateDialogFragment.newInstance(dataState.data)
                        .show(supportFragmentManager, RateDialogFragment::class.java.name)
                }
            }
        }
    }

    private fun openSearch() {
        startActivityWithFade(SearchActivity.getIntent(this))
    }

    private fun openCart() {
        navigator.navigateTo(Screen.CART, null)
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
                    ).putExtra("type", 2)
                )

            }
            ActionType.Championships -> {
                if (checkUserLogin()) {
                    startActivityWithFade(
                        ChampionshipsActivity.getIntent(this)
                    )
                }

            }
            ActionType.MemberShip -> {
                if (checkUserLogin()) {
                    startActivityWithFade(
                        MemberShipActivity.getIntent(this)
                    )
                }

            }
            ActionType.Rate -> {
                startActivityWithFade(RateMeActivity.getIntent(this))
            }
            ActionType.Info -> {
                startActivityWithFade(
                    WebViewActivity.getIntent(this).putExtra(
                        "title",
                        getString(R.string.menu_about)
                    ).putExtra("type", 1)
                )
            }
            ActionType.Status -> {
                if (checkUserLogin()) {
                    startActivity(PointsActivity.getIntent(this))
                }
            }
            ActionType.Wallet -> {
                if (checkUserLogin()) {
                    startActivity(WalletActivity.getIntent(this))
                }
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
        if (getViewDataBinding().drawerLayout.isDrawerOpen(GravityCompat.START))
            getViewDataBinding().drawerLayout.closeDrawer(GravityCompat.START)
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
