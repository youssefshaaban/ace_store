package com.example.mvvm_template.ui.component.main.bottom.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.FragmentHomeBinding
import com.example.mvvm_template.domain.entity.Category

import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    var pagNumber: Int = 1
    var stopLoadMore: Boolean = false
    private val categoryCardAdapter by lazy {
        CategoryCardAdapter(::handleClickCategory)
    }

    private fun handleClickCategory(category: Category) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvCategory.configGridRecycle(2, true)
        getViewDataBinding().rvCategory.adapter = categoryCardAdapter
        getViewDataBinding().rvCategory.addEndlessScroll(::handleLoadMore)
        getViewDataBinding().pager.adapter = SliderAdapter() {}
        viewModel.getCategories(pagNumber)
        TabLayoutMediator(
            getViewDataBinding().tabLayoutDots,
            getViewDataBinding().pager,
            { _, _ -> }).attach()
        sharedViewModel.title.value = getString(R.string.title_home)
    }

    private fun handleLoadMore() {
        if (!stopLoadMore) {
            pagNumber += 1
            viewModel.getCategories(pagNumber)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun observeViewModel() {
        observe(viewModel.observeCategoryLiveDate, ::handleDateStatCategory)
    }

    private fun handleDateStatCategory(dataState: DataState<List<Category>>) {
        when (dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                if (dataState.data.size < 20) {
                    stopLoadMore = true
                }
                categoryCardAdapter.submitList(categoryCardAdapter.currentList + dataState.data)
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
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