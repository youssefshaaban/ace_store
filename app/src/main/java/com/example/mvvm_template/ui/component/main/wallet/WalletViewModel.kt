package com.example.mvvm_template.ui.component.main.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Wallet
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cutomer.ChargeWalletUseCase
import com.example.mvvm_template.domain.interactor.cutomer.GetWalletUseCase
import com.example.mvvm_template.domain.interactor.order.PlaceOrderUseCse
import com.example.mvvm_template.domain.interactor.payment.PaymentInitUseCase

import com.example.mvvm_template.utils.SingleLiveEvent
import com.google.gson.Gson
import com.payfort.fortpaymentsdk.domain.model.FortRequest

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val paymentInitUseCase: PaymentInitUseCase,
    private val chargeWalletUseCase: ChargeWalletUseCase
) :
    ViewModel() {

    private val _walletPrivateLive = MutableLiveData<Wallet>()
    val walletLiveData: LiveData<Wallet> get() = _walletPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive
    private val _forRequestPrivateLive = SingleLiveEvent<FortRequest>()
    val fortRequestLiveData: LiveData<FortRequest> get() = _forRequestPrivateLive
    private val _throwablePrivateLive = MutableLiveData<Throwable>()
    val throwableLive: LiveData<Throwable> get() = _throwablePrivateLive

    private val _successChargeWalletPrivateLive = MutableLiveData<Boolean>()
    val successChargeLiveData: LiveData<Boolean> get() = _successChargeWalletPrivateLive
    fun getWallet() {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            getWalletUseCase.execute(Unit).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _walletPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }

    }

    fun chargeWallet(amount: Double, paymentId: Int, fortId: Any?, merchantRef: Any?) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            try {
                chargeWalletUseCase.execute(
                    RequestChargeWallet(
                        amount = amount,
                        paymentMethodId = paymentId,
                        merchantRef,
                        fortId
                    )
                ).collect {
                    _loaderVisibilityLive.value = false
                    if (it is DataState.Success) {
                        _successChargeWalletPrivateLive.value = true
                    } else if (it is DataState.Error) {
                        _failurePrivateLive.value = it.error
                    }
                }
            } catch (e: Throwable) {
                _throwablePrivateLive.value = e
            }
        }

    }


    fun getForRequest(amount: Double) {
        viewModelScope.launch {
            _loaderVisibilityLive.value = true
            paymentInitUseCase.getFortRequest((amount * 100.0).roundToLong(), "SAR")
                .catch { exception ->
                    if (exception is PaymentInitUseCase.SDKTokenException) {
                        _failurePrivateLive.value = exception.error
                    }
                    _loaderVisibilityLive.value = false
                }.collect {
                _loaderVisibilityLive.value = false
                _forRequestPrivateLive.value = it
            }
        }
    }

}