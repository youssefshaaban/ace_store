package com.example.mvvm_template.ui.component.main.bottom.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.FragmentHomeBinding
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.HomeData
import com.example.mvvm_template.domain.entity.Slider

import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    var pagNumber: Int = 1
    private var hasInitializedRootView = false

    @Inject
    lateinit var appNavigator: AppNavigator
    private val categoryCardAdapter by lazy {
        CategoryCardAdapter(::handleClickCategory)
    }

    private fun handleClickCategory(category: Category) {
        appNavigator.navigateTo(Screen.PRODUCT_BY_CATEGORY, Bundle().apply {
            putInt("cat_id", category.id!!)
            putString("title", category.name)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvCategory.configGridRecycle(2, true)
        getViewDataBinding().rvCategory.adapter = categoryCardAdapter
        getViewDataBinding().rvCategory.addEndlessScroll(::handleLoadMore)
        sharedViewModel.title.value = getString(R.string.title_home)
        if (!hasInitializedRootView) {
            viewModel.getHome()
            hasInitializedRootView = true
        }
    }

    private fun handleLoadMore() {
        if (!viewModel.stopLoadingMore.value!!) {
            pagNumber += 1
            viewModel.getCategories(pagNumber)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun observeViewModel() {
        observe(viewModel.loadingVisibility){
            if (it){
                showLoading()
            }else{
                hideLoading()
            }
        }
        observe(viewModel.error){
            handleFaluir(it)
        }
        observe(viewModel.catogeries){
            categoryCardAdapter.submitList(it)
        }
        observe(viewModel.sliderLiveDate,::handleResourceSlider)
    }

    private fun handleResourceSlider(dataState: DataState<HomeData>?) {
        when(dataState){
            is DataState.Loading->getViewDataBinding().progressSlider.toVisible()
            is DataState.Error->{
                getViewDataBinding().progressSlider.toGone()
                handleFaluir(dataState.error)
            }
            is DataState.Success->{
                getViewDataBinding().pager.adapter = SliderAdapter(dataState.data.sliders) {}
                sharedViewModel.carCount.value=dataState.data.cartCount
                TabLayoutMediator(
                    getViewDataBinding().tabLayoutDots,
                    getViewDataBinding().pager,
                    { _, _ -> }).attach()
                getViewDataBinding().progressSlider.toGone()
            }
        }

    }

    override fun showLoading() {
        getViewDataBinding().progress.toVisible()
    }

    override fun hideLoading() {
        getViewDataBinding().progress.toGone()
    }
}