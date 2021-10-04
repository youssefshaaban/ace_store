package com.example.mvvm_template.ui.component.product_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.ActivityProductDetailBinding
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    val id:Int? by lazy {
        intent.extras?.getInt("ID",0)
    }
    val viewModel:ProductDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().rvReview.configRecycle(true)
        viewModel.getProductById(id!!)
        observe(viewModel.productLiveData,::handleDateStatProductById)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_product_detail
    }

    private fun handleDateStatProductById(dataState: DataState<Product>) {
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

    private fun setData(product: Product) {
        getViewDataBinding().img.loadImage(product.imagePath,R.drawable.bg_no_image)
        getViewDataBinding().name.text=product.name
        getViewDataBinding().tvOldPrice.text="${product.price} ${ product.currency?.name}"
        getViewDataBinding().price.text="${product.priceAfterDiscount} ${ product.currency?.name}"
        getViewDataBinding().description.text=product.descriptionShort
        val adapter=ProductReviewsAdapter()
        adapter.submitList(product.reviews)
        getViewDataBinding().rvReview.adapter=adapter
    }
}