package com.example.mvvm_template.ui.component.main

import androidx.lifecycle.MutableLiveData
import com.example.mvvm_template.ui.base.BaseViewModel


class MainViewModel  :BaseViewModel() {

    val title=MutableLiveData<String>()
}