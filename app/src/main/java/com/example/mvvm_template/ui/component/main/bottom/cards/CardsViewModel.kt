package com.example.mvvm_template.ui.component.main.bottom.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.category.GetCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(val getCardUseCase: GetCardUseCase) : ViewModel() {
    private val cardsDataLiveDate = MutableLiveData<DataState<List<Card>>>()
    val observeCardsLiveDate: LiveData<DataState<List<Card>>> get() = cardsDataLiveDate

    fun getCards(){
        viewModelScope.launch {
            cardsDataLiveDate.value=DataState.Loading
            getCardUseCase.execute(Unit).collect {
                cardsDataLiveDate.value=it
            }
        }
    }
}