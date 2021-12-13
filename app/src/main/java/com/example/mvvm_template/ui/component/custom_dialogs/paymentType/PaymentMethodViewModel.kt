package com.example.mvvm_template.ui.component.custom_dialogs.paymentType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.PaymentMethod
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.order.GetPaymentMethodUseCse
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val getPaymentMethodUseCse: GetPaymentMethodUseCse,
) : ViewModel() {

    val loadingVisiblilty = MutableLiveData(false)
    val paymentMethodLiveDate = MutableLiveData<List<PaymentMethod>>()
    val error = SingleLiveEvent<Failure>()
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
}