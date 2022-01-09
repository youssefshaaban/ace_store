package com.example.mvvm_template.ui.component.main.bottom.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Order
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cart.GetCartUseCase
import com.example.mvvm_template.domain.interactor.order.GetMyOrderUseCse
import com.example.mvvm_template.domain.interactor.order.ReOrderUseCse
import com.example.mvvm_template.utils.LogUtil
import com.example.mvvm_template.utils.RSA
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchesdViewModel @Inject constructor(
    private val getMyOrderUseCse: GetMyOrderUseCse,
    val reOrderUseCse: ReOrderUseCse,
    val getCartUseCase: GetCartUseCase
) : ViewModel() {
    private val _myOrderPrivateLive = MutableLiveData<List<Order>>()
    private val _cartPrivateLive = MutableLiveData<DataState<Cart?>>()
    val cartLiveData: LiveData<DataState<Cart?>> get() = _cartPrivateLive
    val myOrdersLiveData: LiveData<List<Order>> get() = _myOrderPrivateLive
    private val _successReorderPrivateLive = MutableLiveData<Boolean>()
    val successReorderLiveData: LiveData<Boolean> get() = _successReorderPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun getMyOrders() {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            getMyOrderUseCse.execute(Unit).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _myOrderPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }

    fun getCart(){
        viewModelScope.launch {
            getCartUseCase.execute(Unit).collect {
                _cartPrivateLive.value=it
            }
        }
    }
    fun reOrder(id:Int){
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            reOrderUseCse.execute(RateDTO(orderId = id)).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successReorderPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }
}