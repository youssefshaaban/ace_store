package com.example.mvvm_template.ui.component.rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.order.RateOrderUseCse
import com.example.mvvm_template.domain.interactor.order.SkipRateUseCse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateViewModel @Inject constructor(
    private val skipRateUseCse: SkipRateUseCse,
    private val rateOrderUseCse: RateOrderUseCse
) : ViewModel() {
    private val _successPrivateLive = MutableLiveData<Boolean>()
    val successLiveData: LiveData<Boolean> get() = _successPrivateLive
    private val _validationExceptionPrivateLive = MutableLiveData<Throwable>()
    val validationExceptionLiveData: LiveData<Throwable> get() = _validationExceptionPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun rateOrder(rateDTO: RateDTO) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            rateOrderUseCse.execute(rateDTO).catch {
                exception-> _validationExceptionPrivateLive.value=exception
            }.collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successPrivateLive.value = true
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }

    fun skipRateOrder(rateDTO: RateDTO) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            skipRateUseCse.execute(rateDTO).catch {
                    exception-> _validationExceptionPrivateLive.value=exception
            }.collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successPrivateLive.value = true
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }
}