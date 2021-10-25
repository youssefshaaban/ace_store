package com.example.mvvm_template.ui.component.product_detail

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.ActivityProductDetailBinding
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    val id: Int? by lazy {
        intent.extras?.getInt("ID", 0)
    }
    var isAddCart: Boolean?=false
    val viewModel: ProductDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().rvReview.configRecycle(true)
        viewModel.getProductById(id!!)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        setupObservable()
        isAddCart=intent.extras?.getBoolean("isAddCart", false)
        getViewDataBinding().cartContent.setOnClickListener {
            viewModel.addToCart(id,if (isAddCart!!) 0 else 1)
        }
        checkCart()
    }

    private fun checkCart() {
        if (isAddCart!=null&&isAddCart!!){
            getViewDataBinding().cartContent.background=resources.getDrawable(R.drawable.back_strok_solid_color_accent)
            getViewDataBinding().cart.setColorFilter(resources.getColor(R.color.white))
        }else{
            getViewDataBinding().cartContent.background=resources.getDrawable(R.drawable.back_strok_color_accent)
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
           isAddCart=!isAddCart!!
           checkCart()
       }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    private fun setData(product: Product?) {
        product?.let {
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