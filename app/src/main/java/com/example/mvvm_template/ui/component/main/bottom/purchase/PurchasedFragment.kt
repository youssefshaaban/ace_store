package com.example.mvvm_template.ui.component.main.bottom.purchase

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
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.FragmentPurchasedBinding
import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configRecycle

class PurchasedFragment : BaseFragment<FragmentPurchasedBinding>() {

    val sharedViewModel: MainViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().contentEmpty.emptyRecycle.configRecycle(true)
        getViewDataBinding().contentEmpty.emptyRecycle.adapter=PurchasedCardAdapter(){}
        sharedViewModel.title.value=getString(R.string.title_purchases)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_purchased
    }

    override fun observeViewModel() {

    }
}