package com.example.mvvm_template.ui.component.main.bottom.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.FragmentHomeBinding
import com.example.mvvm_template.ui.base.BaseFragment
import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configGridRecycle
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    val sharedViewModel: MainViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().rvCategory.configGridRecycle(2, true)
        getViewDataBinding().rvCategory.adapter = CategoryCardAdapter() {}
        getViewDataBinding().pager.adapter = SliderAdapter() {}
        TabLayoutMediator(
            getViewDataBinding().tabLayoutDots,
            getViewDataBinding().pager,
            { _, _ -> }).attach()

        sharedViewModel.title.value=getString(R.string.title_home)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun observeViewModel() {

    }
}