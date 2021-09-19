package com.example.mvvm_template.ui.component.main.bottom.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.databinding.FragmentCardsBinding

import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configRecycle


class CardsFragment : BaseFragment<FragmentCardsBinding>() {

    val sharedViewModel:MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
        sharedViewModel.title.value=getString(R.string.title_cards)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_cards
    }

    override fun observeViewModel() {

    }
}