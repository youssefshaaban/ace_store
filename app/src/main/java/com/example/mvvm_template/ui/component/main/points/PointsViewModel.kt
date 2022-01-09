package com.example.mvvm_template.ui.component.main.points

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Point
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.cutomer.GetPointUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointsViewModel @Inject constructor(
    private val getPointUseCase: GetPointUseCase,
) :
    ViewModel() {

    private val _pointsPrivateLive = MutableLiveData<Point>()
    val pointsLiveData: LiveData<Point> get() = _pointsPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive


    fun getPoints() {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            getPointUseCase.execute(Unit).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _pointsPrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }

            }
        }
    }


}