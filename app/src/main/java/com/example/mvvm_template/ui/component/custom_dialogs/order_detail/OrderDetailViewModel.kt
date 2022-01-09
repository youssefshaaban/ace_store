package com.example.mvvm_template.ui.component.custom_dialogs.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.order.GetMyOrderUseCse
import com.example.mvvm_template.domain.interactor.order.GetOrderByIdUseCse
import com.example.mvvm_template.utils.LogUtil
import com.example.mvvm_template.utils.RSA
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(private val getOrderByIdUseCse: GetOrderByIdUseCse) : ViewModel() {
    private val _orderDetailPrivateLive = MutableLiveData<Order>()
    val orderDetailLiveData: LiveData<Order> get() = _orderDetailPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun getOrderById(id:Int){
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            getOrderByIdUseCse.execute(id).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _orderDetailPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }
}