package com.example.mvvm_template.ui.component.main.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.dto.RequestOrder
import com.example.mvvm_template.domain.entity.Wallet
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cutomer.GetWalletUseCase
import com.example.mvvm_template.domain.interactor.order.PlaceOrderUseCse
import com.example.mvvm_template.utils.RSA

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
) :
    ViewModel() {

    private val _walletPrivateLive = MutableLiveData<Wallet>()
    val walletLiveData: LiveData<Wallet> get() = _walletPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

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


}