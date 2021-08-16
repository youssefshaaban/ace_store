package com.example.mvvm_template.ui.component.card_categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_template.ui.base.BaseViewModel

class CardViewModel : BaseViewModel() {
    private val searchQueryPrivateLive = MutableLiveData<String>()
    val searchQueryLiveData: LiveData<String> get() = searchQueryPrivateLive

    fun setSarchText(query:String){
        searchQueryPrivateLive.value=query
    }
}