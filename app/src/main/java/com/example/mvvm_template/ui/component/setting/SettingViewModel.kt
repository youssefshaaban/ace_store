package com.example.mvvm_template.ui.component.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestUpdateSetting
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.account.UpdateAccountSettingUseCase
import com.example.mvvm_template.domain.interactor.order.RateOrderUseCse
import com.example.mvvm_template.domain.interactor.order.SkipRateUseCse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val updateAccountSettingUseCase: UpdateAccountSettingUseCase) :
    ViewModel() {
    private val _successPrivateLive = MutableLiveData<RequestUpdateSetting>()
    val successLiveData: LiveData<RequestUpdateSetting> get() = _successPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun saveSetting(requestUpdateSetting: RequestUpdateSetting) {
        viewModelScope.launch {
            _loaderVisibilityLive.value = true
            updateAccountSettingUseCase.execute(requestUpdateSetting).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }


}