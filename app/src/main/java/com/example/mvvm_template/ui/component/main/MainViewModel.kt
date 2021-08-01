package com.example.mvvm_template.ui.component.main

import androidx.lifecycle.MutableLiveData
import com.example.mvvm_template.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor()  :BaseViewModel() {

    val title=MutableLiveData<String>()
}