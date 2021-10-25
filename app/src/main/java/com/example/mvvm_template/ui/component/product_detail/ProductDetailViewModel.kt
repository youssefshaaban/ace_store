package com.example.mvvm_template.ui.component.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cart.AddCartUseCase
import com.example.mvvm_template.domain.interactor.product.GetProductsByIdUseCase
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductsByIdUseCase: GetProductsByIdUseCase,
    private val addCartUseCase: AddCartUseCase
) : ViewModel() {

    val loadingVisiblilty = MutableLiveData(false)
    val productLiveDate = MutableLiveData<Product>()
    val cartLiveData = MutableLiveData<Cart>()
    val error = SingleLiveEvent<Failure>()
    fun getProductById(idProduct: Int) {
        loadingVisiblilty.value = true
        viewModelScope.launch {
            getProductsByIdUseCase.execute(idProduct).collect {
                if (it is DataState.Success) {
                    productLiveDate.value = it.data
                    loadingVisiblilty.value = false
                } else if (it is DataState.Error) {
                    error.value = it.error
                    loadingVisiblilty.value = false
                }
            }
        }
    }

    fun addToCart(idProduct: Int?, quantity: Int?) {
        loadingVisiblilty.value = true
        viewModelScope.launch {
            addCartUseCase.execute(RequestAddCart(productId = idProduct,quantity=quantity)).catch {
                exc->
                loadingVisiblilty.value = false
                error.value=Failure.UnknownError("invalid Id")
            }.collect {
                if (it is DataState.Success) {
                    cartLiveData.value = it.data
                    loadingVisiblilty.value = false
                } else if (it is DataState.Error) {
                    error.value = it.error
                    loadingVisiblilty.value = false
                }
            }
        }
    }
}