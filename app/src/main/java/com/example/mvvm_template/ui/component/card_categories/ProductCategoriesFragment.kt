package com.example.mvvm_template.ui.component.card_categories

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityItemCategoriesBinding
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.ui.component.main.bottom.home.CategoryCardAdapter

import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductCategoriesFragment : BaseFragment<ActivityItemCategoriesBinding>() {
    var pagNumber: Int = 1
    var stopLoadMore: Boolean = false
    val viewModel: ProductsViewModel by activityViewModels()
    val cat_id: Int? by lazy {
        arguments?.getInt("cat_id")
    }
    var search: String? = null

    @Inject
    lateinit var appNavigator: AppNavigator
    private val productCategoryAdapter by lazy {
        ProductCategoryAdapter(::handleClickProduct)
    }

    private fun handleClickProduct(product: Product) {
        appNavigator.navigateTo(Screen.PRODUCT_DETAIL, Bundle().apply {
            putInt("ID", product.id)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionGrid()
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        getViewDataBinding().content.emptyRecycle.adapter = productCategoryAdapter
        onClickListner()
        if (arguments?.getBoolean(
                "isSearch",
                false
            ) == null || requireArguments().getBoolean("isSearch", false)
        ) {
            getViewDataBinding().lastOrder.toGone()
        } else {
            getViewDataBinding().lastOrder.toVisible()
            viewModel.getProductsWithCatId(cat_id, pagNumber)
        }
        getViewDataBinding().content.emptyRecycle.addEndlessScroll(::handleLoadMore)
    }

    private fun handleLoadMore() {
        if (!stopLoadMore) {
            pagNumber += 1
            if (cat_id != null) {
                viewModel.getProductsWithCatId(cat_id, pagNumber)
            } else {
                viewModel.getProductsSearch(search, pagNumber)
            }
        }
    }

    private fun onClickListner() {
        getViewDataBinding().list.setOnClickListener {
            actionList()
        }
        getViewDataBinding().grid.setOnClickListener {
            actionGrid()
        }
    }


    private fun actionGrid() {
        getViewDataBinding().grid.setColorFilter(R.color.colorAccent)
        getViewDataBinding().list.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2, true)
    }


    private fun actionList() {
        getViewDataBinding().list.setColorFilter(R.color.colorAccent)
        getViewDataBinding().grid.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
    }

    private fun handleDateStatCategory(dataState: DataState<List<Product>>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                if (dataState.data.size < 20) {
                    stopLoadMore = true
                }
                productCategoryAdapter.submitList(productCategoryAdapter.currentList + dataState.data)
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_item_categories
    }

    override fun observeViewModel() {
        observe(viewModel.productsLiveData, ::handleDateStatCategory)
        observe(viewModel.searchQueryLiveData) {
            // perform search
            search = it
            pagNumber = 1
            viewModel.getProductsSearch(search, pagNumber)
        }
    }
}