package com.example.mvvm_template.ui.component.auth.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.interactor.account.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val logOutUseCase: LogOutUseCase,) :
    ViewModel() {

    private val successDataLiveDate = MutableLiveData<DataState<Boolean>>()
    val observeSuccess: LiveData<DataState<Boolean>> get() = successDataLiveDate

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.execute(Unit).collect {
                successDataLiveDate.value=it
            }
        }
    }

}