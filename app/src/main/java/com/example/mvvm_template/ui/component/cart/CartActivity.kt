package com.example.mvvm_template.ui.component.cart

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.ActivityCartBinding
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
  private val viewModel: CartViewModel by viewModels()
  private val paymentOnlineViewModel:PaymentOnlineViewModel by viewModels()
  var cartData:Cart?=null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
    getViewDataBinding().appBar.title.text = getString(R.string.title_cart)
    getViewDataBinding().contentEmpty.emptyRecycle.configRecycle(true)
    getViewDataBinding().contentEmpty.emptyRecycle.setEmptyView(getViewDataBinding().contentEmpty.contentEmptyView)
    getViewDataBinding().contentEmpty.txt.text = resources.getString(R.string.there_is_no_product)
    viewModel.getCart()
    setupObservable()
    getViewDataBinding().order.setOnClickListener {
      paymentOnlineViewModel.encryptCard()
      //paymentOnlineViewModel.getProfile()
    }
  }


  private fun setupObservable() {
    observe(viewModel.cartLiveData, ::handelResourceCart)
    observe(paymentOnlineViewModel.observProfile,::handleProfile)
  }

  private fun handleProfile(dataState: DataState<Profile>) {
    when (dataState) {
      is DataState.Loading -> showLoading()
      is DataState.Success -> {
        hideLoading()
        paymentOnlineViewModel.amount=cartData?.total
        paymentOnlineViewModel.startSDK(profile = dataState.data)
        paymentOnlineViewModel.sdkSession.start(this)
      }
      is DataState.Error -> {
        hideLoading()
        handleFaluir(dataState.error)
      }
    }
  }

  private fun handelResourceCart(dataState: DataState<Cart>) {
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

  private fun setData(data: Cart) {
    cartData=data
    getViewDataBinding().contentEmpty.emptyRecycle.adapter =
      CartAdapter(data.products!!, ::handleClickAddCart)
    if (data.code != null) {
      getViewDataBinding().subTotalContent.toVisible()
      getViewDataBinding().discountContent.toVisible()
    } else {
      getViewDataBinding().subTotalContent.toGone()
      getViewDataBinding().discountContent.toGone()
    }
    getViewDataBinding().total.text = data.total.toString() + " " + getString(R.string.currencyKSA)
    getViewDataBinding().subTotal.text =
      data.subTotal.toString() + " " + getString(R.string.currencyKSA)
    getViewDataBinding().discount.text =
      data.discountValue.toString() + " " + getString(R.string.currencyKSA)
  }

  private fun handleClickAddCart(productId: Int, quantity: Int) {
    viewModel.addToCart(productId, quantity)
  }

  override fun getLayoutId(): Int {
    return R.layout.activity_cart
  }



}