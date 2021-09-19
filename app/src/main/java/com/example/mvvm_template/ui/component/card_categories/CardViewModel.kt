package com.example.mvvm_template.ui.component.card_categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor() : ViewModel() {
    private val searchQueryPrivateLive = MutableLiveData<String>()
    val searchQueryLiveData: LiveData<String> get() = searchQueryPrivateLive

    fun setSarchText(query:String){
        searchQueryPrivateLive.value=query
    }
}