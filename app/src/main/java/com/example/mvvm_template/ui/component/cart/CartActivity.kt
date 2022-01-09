package com.example.mvvm_template.ui.component.cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.*
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityCartBinding
import com.example.mvvm_template.domain.entity.*
import com.example.mvvm_template.ui.component.custom_dialogs.paymentType.DialogPaymentMethodFragment
import com.example.mvvm_template.ui.component.payment.PLACE_ORDER
import com.example.mvvm_template.ui.component.payment.PaymentActivity
import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
    private val viewModel: CartViewModel by viewModels()
    var cartData: Cart? = null

    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text = getString(R.string.title_cart)
        getViewDataBinding().contentEmpty.emptyRecycle.configRecycle(true)
        getViewDataBinding().contentEmpty.emptyRecycle.setEmptyView(getViewDataBinding().contentEmpty.contentEmptyView)
        getViewDataBinding().contentEmpty.txt.text =
            resources.getString(R.string.there_is_no_product)
        viewModel.getCart()
        setupObservable()
        getViewDataBinding().order.setOnClickListener {
            if (checkUserLogin()) {
                DialogPaymentMethodFragment().apply {
                    arguments=Bundle().apply {
                        putDouble("amount",cartData?.total!!)
                        putString("currency",cartData?.products?.firstOrNull()?.name)
                        putString("type", PLACE_ORDER)
                    }
                }.showDialog(
                    supportFragmentManager
                ){
                    startActivity(PaymentActivity.getIntent(this).putExtra("amount",cartData?.total)
                        .putExtra("currency",cartData?.products?.firstOrNull()?.name)
                        .putExtra("type",PLACE_ORDER)
                    )
                }
            }
        }
    }




    private fun setupObservable() {
        observe(viewModel.cartLiveData, ::handelResourceCart)
        observe(viewModel.loaderVisibilityLiveData) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        observe(viewModel.orderIdLiveData) {
            appNavigator.navigateTo(Screen.HOME, Bundle().apply {
                putBoolean(GO_TO_HISTORY, true)
            })
        }
        observe(viewModel.faluireLiveData, ::handleFaluir)
    }


    private fun handelResourceCart(dataState: DataState<Cart?>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                setData(dataState.data)
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: Cart?) {
        if (data != null) {
            cartData = data
            getViewDataBinding().contentEmpty.emptyRecycle.adapter =
                CartAdapter(data.products!!, ::handleClickAddCart)
            if (data.code != null) {
                getViewDataBinding().subTotalContent.toVisible()
                getViewDataBinding().discountContent.toVisible()
            } else {
                getViewDataBinding().subTotalContent.toGone()
                getViewDataBinding().discountContent.toGone()
            }
            getViewDataBinding().total.text = "${data.total}   ${getString(R.string.currencyKSA)}"
            getViewDataBinding().subTotal.text =
                "${data.subTotal}   ${getString(R.string.currencyKSA)}"
            getViewDataBinding().discount.text =
                "${data.discountValue}   ${getString(R.string.currencyKSA)}"
            val intent = Intent(CART_UPDATE)
            intent.putExtra(CART_COUNT, cartData?.products?.sumOf { prod->prod.quantity})
            LocalBroadcastManager.getInstance(getViewDataBinding().root.context)
                .sendBroadcast(intent)
        }
    }

    private fun handleClickAddCart(productId: Int, quantity: Int) {
        viewModel.addToCart(productId, quantity)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cart
    }


}