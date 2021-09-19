package com.example.mvvm_template.ui.component.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.account.GetProfileUseCase
import com.example.mvvm_template.domain.interactor.account.LogOutUseCase
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    val title = MutableLiveData<String>()
    private val profileDataLiveDate = MutableLiveData<DataState<Profile>>()
    val observProfile: LiveData<DataState<Profile>> get() = profileDataLiveDate
    private val successDataLiveDate = MutableLiveData<DataState<Boolean>>()
    val observeSuccess: LiveData<DataState<Boolean>> get() = successDataLiveDate
    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute(null).collect {
                profileDataLiveDate.value = it
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.execute(null).collect {
                successDataLiveDate.value=it
            }
        }
    }
}