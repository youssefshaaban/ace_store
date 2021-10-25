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
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cart.AddCartUseCase
import com.example.mvvm_template.domain.interactor.product.GetProductsUseCase
import com.example.mvvm_template.utils.FIRST_PAGE
import com.example.mvvm_template.utils.SingleLiveEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    val addCartUseCase: AddCartUseCase
) :
    ViewModel() {
    private val searchQueryPrivateLive = SingleLiveEvent<String>()
    val searchQueryLiveData: LiveData<String> get() = searchQueryPrivateLive

    //    private val _productsPrivateLive = MutableLiveData<DataState<List<Product>>>()
//    val productsLiveData: LiveData<DataState<List<Product>>> get() = _productsPrivateLive
    private val _productsPrivateLive = MutableLiveData<ArrayList<Product>>()
    val productsLiveData: LiveData<ArrayList<Product>> get() = _productsPrivateLive
    private val _notifyPositionCart = SingleLiveEvent<Int>()
    val notifyPositionCartLiveDate get() = _notifyPositionCart
    private val _errorFaliureCalling = SingleLiveEvent<Failure>()
    val errorFaliureCallingLiveDate = _errorFaliureCalling
    private val _loadingVisiblilty = MutableLiveData(false)
    val loadinVisibilityObserve get() = _loadingVisiblilty
    var stopLoadingMore = false
    fun setSarchText(query: String) {
        searchQueryPrivateLive.value = query
    }

    fun getProducts(cat_id: Int?=null, searchText: String? = null, pageIndex: Int) {
        viewModelScope.launch {
            _loadingVisiblilty.value = true
            getProductsUseCase.execute(
                RequestGetProductDto(
                    PageIndex = pageIndex,
                    CategoryId = cat_id?.toString(), SearchText = searchText
                )
            ).collect {
                _loadingVisiblilty.value = false
                if (it is DataState.Success) {
                    if (it.data.size < 20) {
                        stopLoadingMore = true
                    }
                    if (pageIndex == FIRST_PAGE)
                        _productsPrivateLive.value = ArrayList(it.data)
                    else {
                        _productsPrivateLive.value?.addAll(it.data)
                        _productsPrivateLive.value = _productsPrivateLive.value
                    }
                } else if (it is DataState.Error) {
                    _errorFaliureCalling.value = it.error
                }
            }
        }
    }

    fun addToCart(productId: Int, position: Int, isAddCart: Boolean) {
        _loadingVisiblilty.value = true
        viewModelScope.launch {
            addCartUseCase.execute(
                RequestAddCart(productId, if (isAddCart) 1 else 0)
            ).collect {
                _loadingVisiblilty.value = false
                if (it is DataState.Success) {
                    _productsPrivateLive.value?.get(position)?.isAtCart=isAddCart
                    _notifyPositionCart.value = position
                } else if (it is DataState.Error) {
                    _errorFaliureCalling.value = it.error
                }
            }
        }
    }
}