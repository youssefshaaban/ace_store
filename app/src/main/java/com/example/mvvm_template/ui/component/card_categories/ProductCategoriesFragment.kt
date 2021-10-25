package com.example.mvvm_template.ui.component.card_categories

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.ActivityItemCategoriesBinding
import com.example.mvvm_template.domain.entity.Product

import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductCategoriesFragment : BaseFragment<ActivityItemCategoriesBinding>() {
    var pagNumber: Int = FIRST_PAGE
    val viewModel: ProductsViewModel by activityViewModels()
    val cat_id: Int? by lazy {
        arguments?.getInt("cat_id")
    }
    var search: String? = null

    @Inject
    lateinit var appNavigator: AppNavigator
    private val productCategoryAdapter by lazy {
        ProductCategoryAdapter(::handleClickProduct,::handleAddToCart)
    }

    private fun handleAddToCart(product: Product,position:Int) {
        viewModel.addToCart(product.id,position,!product.isAtCart)
    }

    private fun handleClickProduct(product: Product) {
        appNavigator.navigateTo(Screen.PRODUCT_DETAIL, Bundle().apply {
            putInt("ID", product.id)
            putBoolean("isAddCart",product.isAtCart)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionGrid()
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        getViewDataBinding().content.emptyRecycle.adapter = productCategoryAdapter
        getViewDataBinding().lastOrder.toGone()
        onClickListner()
        if (arguments?.getBoolean(
                "isSearch",
                false
            ) == null || requireArguments().getBoolean("isSearch", false)
        ) {
            //getViewDataBinding().lastOrder.toGone()
        } else {
           // getViewDataBinding().lastOrder.toVisible()
            viewModel.getProducts(cat_id, pageIndex = pagNumber)
        }
        getViewDataBinding().content.emptyRecycle.addEndlessScroll(::handleLoadMore)
    }

    private fun handleLoadMore() {
        if (!viewModel.stopLoadingMore) {
            pagNumber += 1
            viewModel.getProducts(cat_id = cat_id,searchText = search, pagNumber)
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
        getViewDataBinding().grid.setColorFilter(resources.getColor(R.color.colorAccent))
        getViewDataBinding().list.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2, true)
    }


    private fun actionList() {
        getViewDataBinding().list.setColorFilter(resources.getColor(R.color.colorAccent))
        getViewDataBinding().grid.setColorFilter(Color.parseColor("#bcbcbc"))
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
    }

    private fun handleDateStatCategory(products: List<Product>) {
        productCategoryAdapter.submitList(productCategoryAdapter.currentList + products)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_item_categories
    }

    override fun observeViewModel() {
        observe(viewModel.productsLiveData, ::handleDateStatCategory)
        observe(viewModel.loadinVisibilityObserve){
            if (it){
                showLoading()
            }else{
                hideLoading()
            }
        }
        observe(viewModel.notifyPositionCartLiveDate,::handlDataChangeCart)
        observe(viewModel.searchQueryLiveData) {
            // perform search
            search = it
            pagNumber = FIRST_PAGE
            viewModel.getProducts(searchText = search,pageIndex= pagNumber)
        }
        observe(viewModel.errorFaliureCallingLiveDate){
            faluir->
            faluir?.let {
                handleFaluir(it)
            }
        }
    }

    private fun handlDataChangeCart(position: Int?) {
        position?.let {
            getViewDataBinding().content.emptyRecycle.adapter?.notifyItemChanged(position)
        }
    }

}