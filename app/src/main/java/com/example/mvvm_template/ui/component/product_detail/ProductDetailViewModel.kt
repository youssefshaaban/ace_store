package com.example.mvvm_template.ui.component.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.interactor.product.GetProductsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val getProductsByIdUseCase: GetProductsByIdUseCase):ViewModel() {
    private val productPrivateLive = MutableLiveData<DataState<Product>>()
    val productLiveData: LiveData<DataState<Product>> get() = productPrivateLive

    fun getProductById(idProduct:Int){
        productPrivateLive.value=DataState.Loading
        viewModelScope.launch {
            getProductsByIdUseCase.execute(idProduct).collect {
                productPrivateLive.value=it
            }
        }
    }
}