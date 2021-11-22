package com.example.mvvm_template.ui.component.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.ActivityCartBinding
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.ui.component.main.bottom.cards.CardsViewModel
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.toGone
import com.example.mvvm_template.utils.toVisible
import com.payfort.fortpaymentsdk.FortSdk
import com.payfort.fortpaymentsdk.callbacks.FortCallBackManager
import com.payfort.fortpaymentsdk.callbacks.FortCallback
import com.payfort.fortpaymentsdk.callbacks.FortInterfaces

import com.payfort.fortpaymentsdk.domain.model.FortRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
    val viewModel: CartViewModel by viewModels()
    lateinit var fortCallback: FortCallBackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.setText(getString(R.string.title_cart))
        getViewDataBinding().contentEmpty.emptyRecycle.configRecycle(true)
        getViewDataBinding().contentEmpty.emptyRecycle.setEmptyView(getViewDataBinding().contentEmpty.contentEmptyView)
        getViewDataBinding().contentEmpty.txt.text=resources.getString(R.string.there_is_no_product)
        fortCallback = FortCallback()
        viewModel.getCart()
        setupObservable()
        getViewDataBinding().pay.setOnClickListener {
            viewModel.getForRequest()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fortCallback.onActivityResult(requestCode,resultCode,data);
    }
    private fun setupObservable() {
        observe(viewModel.cartLiveData, ::handelResourceCart)
        observe(viewModel.fortRequestLiveData,::payWithSdk)
    }

    private fun payWithSdk(fortRequest:FortRequest){
        FortSdk.getInstance().registerCallback(this,fortRequest,FortSdk.ENVIRONMENT.TEST,5,fortCallback,true,object :FortInterfaces.OnTnxProcessed{
            override fun onCancel(p0: MutableMap<String, Any>?, p1: MutableMap<String, Any>?) {
                Log.e("cancel",p0.toString())
            }

            override fun onSuccess(p0: MutableMap<String, Any>?, p1: MutableMap<String, Any>?) {
              Log.e("success",p0.toString())
            }

            override fun onFailure(p0: MutableMap<String, Any>?, p1: MutableMap<String, Any>?) {
                Log.e("onFailure",p0.toString())
            }

        })
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
        getViewDataBinding().contentEmpty.emptyRecycle.adapter=CartAdapter(data.products!!,::handleClickAddCart)
        if (data.code!=null){
            getViewDataBinding().subTotalContent.toVisible()
            getViewDataBinding().discountContent.toVisible()
        }else{
            getViewDataBinding().subTotalContent.toGone()
            getViewDataBinding().discountContent.toGone()
        }
        getViewDataBinding().total.text=data.total.toString() +" "+ getString(R.string.currencyKSA)
        getViewDataBinding().subTotal.text=data.subTotal.toString()+" "+getString(R.string.currencyKSA)
        getViewDataBinding().discount.text=data.discountValue.toString()+" "+getString(R.string.currencyKSA)

    }

    private fun handleClickAddCart(productId: Int, quantity: Int) {
        viewModel.addToCart(productId,quantity)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cart
    }


}