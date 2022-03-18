package com.example.mvvm_template.ui.component.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cart.AddCartUseCase
import com.example.mvvm_template.domain.interactor.cart.GetCartUseCase
import com.example.mvvm_template.domain.interactor.order.PlaceOrderUseCse


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addCartUseCase: AddCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val placeOrderUseCse: PlaceOrderUseCse
) :
    ViewModel() {

    private val _cartPrivateLive = MutableLiveData<DataState<Cart?>>()
    val cartLiveData: LiveData<DataState<Cart?>> get() = _cartPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    private val _idOrderPrivateLive = MutableLiveData<Int>()
    val orderIdLiveData: LiveData<Int> get() = _idOrderPrivateLive
    fun addToCart(productId: Int, quantity: Int,playerId:String?=null) {
        viewModelScope.launch {
            _cartPrivateLive.value = DataState.Loading
            addCartUseCase.execute(
                RequestAddCart(productId, quantity)
            ).collect {
                _cartPrivateLive.value = it
            }
        }
    }

    fun placeOrder(paymentId: Int) {

        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            placeOrderUseCse.execute(RequestOrder(paymentMethodId = paymentId))
            .collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _idOrderPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }

    }
    fun getCart() {
        viewModelScope.launch {
            _cartPrivateLive.value = DataState.Loading
            getCartUseCase.execute(Unit).collect {
                _cartPrivateLive.value = it
            }
        }
    }


}