package com.example.mvvm_template.ui.component.card_categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.interactor.cart.AddCartUseCase
import com.example.mvvm_template.domain.interactor.product.GetProductsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    val addCartUseCase: AddCartUseCase
                                            ) :
    ViewModel() {
    private val searchQueryPrivateLive = MutableLiveData<String>()
    val searchQueryLiveData: LiveData<String> get() = searchQueryPrivateLive

    private val _productsPrivateLive = MutableLiveData<DataState<List<Product>>>()
    val productsLiveData: LiveData<DataState<List<Product>>> get() = _productsPrivateLive

    private val _addToCartPrivateLive = MutableLiveData<DataState<Cart>>()
    val addCartLiveData: LiveData<DataState<Cart>> get() = _addToCartPrivateLive

    fun setSarchText(query: String) {
        searchQueryPrivateLive.value = query
    }

    fun getProductsWithCatId(cat_id: Int?, pageIndex: Int) {
        viewModelScope.launch {
            _productsPrivateLive.value = DataState.Loading
            getProductsUseCase.execute(
                RequestGetProductDto(
                    PageIndex = pageIndex,
                    CategoryId = cat_id?.toString()
                )
            ).collect {
                _productsPrivateLive.value =it
            }
        }
    }
    fun getProductsSearch(search:String?=null, pageIndex: Int) {
        viewModelScope.launch {
            _productsPrivateLive.value = DataState.Loading
            getProductsUseCase.execute(
                RequestGetProductDto(
                    PageIndex = pageIndex,
                    SearchText = search
                )
            ).collect {
                _productsPrivateLive.value =it
            }
        }
    }

    fun addToCart(productId:Int){
        viewModelScope.launch {
            _addToCartPrivateLive.value = DataState.Loading
            addCartUseCase.execute(
                RequestAddCart(productId,1)
            ).collect {
                _addToCartPrivateLive.value =it
            }
        }
    }
}