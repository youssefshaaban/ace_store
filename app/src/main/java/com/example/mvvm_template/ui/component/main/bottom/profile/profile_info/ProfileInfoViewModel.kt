package com.example.mvvm_template.ui.component.main.bottom.profile.profile_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.UploadFileUseCase
import com.example.mvvm_template.domain.interactor.account.GetProfileUseCase
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val profileDataLiveDate = MutableLiveData<DataState<Profile>>()
    val observeProfile: LiveData<DataState<Profile>> get() = profileDataLiveDate
    private val listStrDataLiveDate = MutableLiveData<DataState<List<String>>>()
    val pathesLiveData: LiveData<DataState<List<String>>> get() = listStrDataLiveDate
    private val validationExceptionMutable = SingleLiveEvent<Int>()
    val validationExceptionLiveDate: LiveData<Int> get() = validationExceptionMutable

    var pathImage: String? = null

    fun getProfile() {
        profileDataLiveDate.value = DataState.Loading
        viewModelScope.launch {
            getProfileUseCase.execute(Unit).collect {
                profileDataLiveDate.value = it
            }
        }
    }

    fun updateProfile(email: String, firstName: String, lastName: String) {
        profileDataLiveDate.value = DataState.Loading
        viewModelScope.launch {
            updateProfileUseCase.execute(
                UpdateProfileUseCase.UpdateRequestProfile(
                    email = email,
                    firstName = firstName, lastName = lastName, imageName = pathImage
                )
            ).catch { exception ->
                validationExceptionMutable.value =
                    (exception as? UpdateProfileUseCase.UpdateProfileValidationException)?.validationType
            }.collect {
                profileDataLiveDate.value = it
            }
        }
    }

    fun uploadFile(file: File) {
        listStrDataLiveDate.value = DataState.Loading
        viewModelScope.launch {
            uploadFileUseCase.execute(listOf(file)).collect {
                listStrDataLiveDate.value = it
            }
        }
    }


}