package com.example.mvvm_template.ui.component.product_detail

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.CART_COUNT
import com.example.mvvm_template.core.common.CART_UPDATE
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityProductDetailBinding
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import com.example.mvvm_template.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    val id: Int? by lazy {
        intent.extras?.getInt("ID", 0)
    }
    private var isBuyNow = false
    private var isAddCart: Boolean? = false
    private var selectProduct: Product? = null
    val viewModel: ProductDetailViewModel by viewModels()

    @Inject
    lateinit var navigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().rvReview.configRecycle(true)
        viewModel.getProductById(id!!)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        setupObservable()
        isAddCart = intent.extras?.getBoolean("isAddCart", false)
        getViewDataBinding().cartContent.setOnClickListener {
            handleClickCart(false)
        }
        checkCart()
        getViewDataBinding().buy.setOnClickListener {
            handleClickCart(true)
        }
    }

    private fun handleClickCart(isBuy: Boolean) {
        isBuyNow = isBuy
        selectProduct?.let {
            if (selectProduct!!.productType == 2) {
                viewModel.addToCart(selectProduct?.id, isAddCart = !selectProduct!!.isAtCart)
            } else if (selectProduct!!.productType == 1) {
                // show dialog player id
                showDialog(selectProduct!!)
            }
        }
    }


    private fun showDialog(product: Product) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.txt_title_for_player_id))

// Set up the input
        val input = EditText(this)
        input.gravity = Gravity.CENTER
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(getString(R.string.player_id))
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which ->
            // Here you get get input text from the Edittext
            val player_id = input.text.toString()
            if (!player_id.isNullOrEmpty()) {
                viewModel.addToCart(
                    product.id,
                    playerId = player_id,
                    isAddCart = !product.isAtCart
                )
                dialog.dismiss()
            } else {
                getViewDataBinding().buy.showToast(
                    getString(R.string.message_should_enter_player_id),
                    1000
                )
            }
        }
        builder.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }


    private fun checkCart() {
        if (isAddCart != null && isAddCart!!) {
            getViewDataBinding().cartContent.background =
                resources.getDrawable(R.drawable.back_strok_solid_color_accent)
            getViewDataBinding().cart.setColorFilter(resources.getColor(R.color.white))
        } else {
            getViewDataBinding().cartContent.background =
                resources.getDrawable(R.drawable.back_strok_color_accent)
            getViewDataBinding().cart.setColorFilter(resources.getColor(R.color.colorAccent))
        }
    }

    private fun setupObservable() {
        observe(viewModel.productLiveDate, ::setData)
        observe(viewModel.loadingVisiblilty) {
            if (it)
                showLoading()
            else
                hideLoading()
        }
        observe(viewModel.error) {
            handleFaluir(it)
        }
        observe(viewModel.cartLiveData, ::handlCart)
    }

    private fun handlCart(cart: Cart?) {
        cart?.let {
            val intent = Intent(CART_UPDATE)
            intent.putExtra(CART_COUNT, cart.products?.sumOf { prod -> prod.quantity })
            LocalBroadcastManager.getInstance(getViewDataBinding().root.context)
                .sendBroadcast(intent)
            if (isBuyNow) {
                navigator.navigateTo(Screen.CART)
            } else {
                isAddCart = !isAddCart!!
                checkCart()
            }

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    private fun setData(product: Product?) {
        product?.let {
            selectProduct = product
            getViewDataBinding().appBar.title.text = product.name
            getViewDataBinding().img.loadImage(product.imagePath, R.drawable.bg_no_image)
            getViewDataBinding().name.text = product.name
            getViewDataBinding().tvOldPrice.text =
                "${product.price ?: ""} ${product.currency?.symbol}"
            getViewDataBinding().price.text =
                "${product.priceAfterDiscount} ${product.currency?.symbol}"
            getViewDataBinding().description.text = product.descriptionShort
            val adapter = ProductReviewsAdapter()
            getViewDataBinding().rvReview.adapter = adapter
            adapter.submitList(product.reviews)
        }

    }
}