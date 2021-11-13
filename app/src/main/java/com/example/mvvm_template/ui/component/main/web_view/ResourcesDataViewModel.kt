package com.example.mvvm_template.ui.component.main.web_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Resource
import com.example.mvvm_template.domain.interactor.resources.UseCaseFactory

import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesDataViewModel @Inject constructor(private val resourcesUseCase: UseCaseFactory) :
    ViewModel() {
    private val resourceLiveDate = SingleLiveEvent<DataState<Resource>>()
    val getResourcesObserveSuccess: LiveData<DataState<Resource>> get() = resourceLiveDate

    fun getResources(type: Int) {
        viewModelScope.launch {
            resourceLiveDate.value = DataState.Loading
            resourcesUseCase.createUseCaseResource(type).getResource()
                .collect {
                    resourceLiveDate.value = it
                }
        }
    }

}