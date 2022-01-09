package com.example.mvvm_template.ui.component.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cutomer.ChargeWalletUseCase
import com.example.mvvm_template.domain.interactor.order.PlaceOrderUseCse
import com.example.mvvm_template.utils.LogUtil
import com.example.mvvm_template.utils.RSA
import com.google.gson.Gson

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val placeOrderUseCse: PlaceOrderUseCse,private val chargeWalletUseCase: ChargeWalletUseCase
):ViewModel() {

    private val _idOrderPrivateLive = MutableLiveData<Int>()
    val orderIdLiveData: LiveData<Int> get() = _idOrderPrivateLive
    private val _successChargeWalletPrivateLive = MutableLiveData<Boolean>()
    val successChargeLiveData: LiveData<Boolean> get() = _successChargeWalletPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _throwablePrivateLive = MutableLiveData<Throwable>()
    val throwableLive: LiveData<Throwable> get() = _throwablePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun placeOrder(paymentId: Int, cardData: CardData) {

        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            LogUtil.error("json", Gson().toJson(cardData))
            placeOrderUseCse.execute(
                RequestOrder(RSA.enccriptData(Gson().toJson(cardData)), paymentId)
            ).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _idOrderPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }

    }

    fun chargeWallet( amount: Double,paymentId: Int, cardData: CardData) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            LogUtil.error("json", Gson().toJson(cardData))
            chargeWalletUseCase.execute(
                RequestChargeWallet(amount, RSA.enccriptData(Gson().toJson(cardData)), paymentId)
            ).catch { exception->_throwablePrivateLive.value=exception}.collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successChargeWalletPrivateLive.value = true
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }

    }

    data class CardData(
        val number: Long,
        val exp_month: Int,
        val exp_year: Int,
        val cvc: Int,
        val name: String
    )
}