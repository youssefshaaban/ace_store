package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.order.GetPaymentMethodUseCse
import com.example.mvvm_template.domain.interactor.order.PlaceOrderUseCse
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val getPaymentMethodUseCse: GetPaymentMethodUseCse,private val placeOrderUseCse: PlaceOrderUseCse) : ViewModel() {

    val loadingVisiblilty = MutableLiveData(false)
    val paymentMethodLiveDate = MutableLiveData<List<PaymentMethod>>()
    val error = SingleLiveEvent<Failure>()
    private val _idOrderPrivateLive = MutableLiveData<Int>()
    val orderIdLiveData: LiveData<Int> get() = _idOrderPrivateLive

    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive
    fun getPaymentMethod() {
        loadingVisiblilty.value = true
        viewModelScope.launch {
            getPaymentMethodUseCse.execute(Unit).collect {
                if (it is DataState.Success) {
                    paymentMethodLiveDate.value = it.data
                    loadingVisiblilty.value = false
                } else if (it is DataState.Error) {
                    error.value = it.error
                    loadingVisiblilty.value = false
                }
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
                        error.value = it.error
                    }
                }
        }

    }
}