package com.example.mvvm_template.ui.component.main.bottom.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseFragment
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.databinding.FragmentCardsBinding
import com.example.mvvm_template.domain.entity.Card

import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import kotlinx.android.synthetic.main.nav_header_main.view.*


class CardsFragment : BaseFragment<FragmentCardsBinding>() {

    val sharedViewModel:MainViewModel by activityViewModels()
    val viewModel:CardsViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
        sharedViewModel.title.value=getString(R.string.title_cards)
        viewModel.getCards()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_cards
    }

    override fun observeViewModel() {
        observe(viewModel.observeCardsLiveDate,::handleDataStatCards)
    }

    private fun handleDataStatCards(dataState: DataState<List<Card>>) {
        when (dataState) {
            is DataState.Loading-> showLoading()
            is DataState.Success -> {
                hideLoading()
                getViewDataBinding().content.emptyRecycle.adapter=CardsAdapter(dataState.data){

                }
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }
}