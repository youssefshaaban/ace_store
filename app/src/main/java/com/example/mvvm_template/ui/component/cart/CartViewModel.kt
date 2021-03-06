package com.example.mvvm_template.ui.component.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.interactor.cart.AddCartUseCase
import com.example.mvvm_template.domain.interactor.cart.GetCartUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addCartUseCase: AddCartUseCase,
    private val getCartUseCase: GetCartUseCase
                                            ) :
    ViewModel() {

    private val _cartPrivateLive = MutableLiveData<DataState<Cart>>()
    val cartLiveData: LiveData<DataState<Cart>> get() = _cartPrivateLive


    fun addToCart(productId:Int,quantity:Int){
        viewModelScope.launch {
            _cartPrivateLive.value = DataState.Loading
            addCartUseCase.execute(
                RequestAddCart(productId,quantity)
            ).collect {
                _cartPrivateLive.value =it
            }
        }
    }
    fun getCart(){
        viewModelScope.launch {
            _cartPrivateLive.value = DataState.Loading
            getCartUseCase.execute(Unit).collect {
                _cartPrivateLive.value =it
            }
        }
    }
}