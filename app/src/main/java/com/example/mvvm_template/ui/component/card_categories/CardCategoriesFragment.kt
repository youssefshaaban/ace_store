package com.example.mvvm_template.ui.component.card_categories

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.ActivityItemCategoriesBinding

import com.example.mvvm_template.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardCategoriesFragment : BaseFragment<ActivityItemCategoriesBinding>() {

    val viewModel:CardViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configGridRecycle(2,true)
        getViewDataBinding().content.emptyRecycle.setEmptyView(getViewDataBinding().content.contentEmptyView)
        onClickListner()
        if (arguments?.getBoolean("isSearch",false)==null||requireArguments().getBoolean("isSearch",false)){
            getViewDataBinding().lastOrder.toGone()
        }else{
            getViewDataBinding().lastOrder.toVisible()
        }
    }

    private fun onClickListner() {
        getViewDataBinding().list.setOnClickListener {
            getViewDataBinding().list.setColorFilter(R.color.colorAccent)
            getViewDataBinding().grid.setColorFilter(Color.parseColor("#bcbcbc"))
            getViewDataBinding().content.emptyRecycle.configRecycle(true)
        }
        getViewDataBinding().grid.setOnClickListener {
            getViewDataBinding().grid.setColorFilter(R.color.colorAccent)
            getViewDataBinding().list.setColorFilter(Color.parseColor("#bcbcbc"))
            getViewDataBinding().content.emptyRecycle.configGridRecycle(2,true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_item_categories
    }

    override fun observeViewModel() {
        observe(viewModel.searchQueryLiveData){
            // perform search
            getViewDataBinding().root.showToast(it,10000)
        }
    }
}