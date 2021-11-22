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
import com.example.mvvm_template.core.navigation.AppNavigator
import com.example.mvvm_template.core.navigation.Screen
import com.example.mvvm_template.databinding.FragmentCardsBinding
import com.example.mvvm_template.domain.entity.Card

import com.example.mvvm_template.ui.component.main.MainViewModel
import com.example.mvvm_template.utils.configRecycle
import com.example.mvvm_template.utils.loadImage
import com.example.mvvm_template.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding>() {

    val sharedViewModel:MainViewModel by activityViewModels()
    val viewModel:CardsViewModel by viewModels()
    var cardsAdapter: CardsAdapter?=null
    private var hasInitializedRootView = false

    @Inject
    lateinit var appNavigator:AppNavigator
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().content.emptyRecycle.configRecycle(true)
        sharedViewModel.title.value=getString(R.string.title_cards)
        if (!hasInitializedRootView) {
            viewModel.getCards()
            hasInitializedRootView = true
        }
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
                cardsAdapter=CardsAdapter(dataState.data){
                    appNavigator.navigateTo(Screen.PRODUCT_BY_CATEGORY,Bundle().apply {
                        putInt("cat_id",it.id!!)
                        putString("title",it.name)
                    })
                }
                getViewDataBinding().content.emptyRecycle.adapter=cardsAdapter
                cardsAdapter?.collapseAllGroup()
            }
            is DataState.Error -> {
                hideLoading()
                handleFaluir(dataState.error)
            }
        }
    }
}