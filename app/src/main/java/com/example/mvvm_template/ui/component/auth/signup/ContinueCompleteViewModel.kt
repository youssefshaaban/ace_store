package com.example.mvvm_template.ui.component.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase

import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinueCompleteViewModel @Inject constructor(private val updateProfileUseCase: UpdateProfileUseCase) :
    ViewModel() {
    private val profileLiveDate = SingleLiveEvent<DataState<Profile>>()
    val profileUpdateSuccess: LiveData<DataState<Profile>> get() = profileLiveDate
    private val validationExceptionMutable = SingleLiveEvent<Int>()
    val validationExceptionLiveDate: LiveData<Int> get() = validationExceptionMutable

    fun completeRegister(updateRequestProfile: UpdateProfileUseCase.UpdateRequestProfile) {
        viewModelScope.launch {
            profileLiveDate.value = DataState.Loading
            updateProfileUseCase.execute(updateRequestProfile).catch { exception ->
                validationExceptionMutable.value =
                    (exception as? UpdateProfileUseCase.UpdateProfileValidationException)?.validationType
            }.collect {
                profileLiveDate.value = it
            }
        }
    }

}