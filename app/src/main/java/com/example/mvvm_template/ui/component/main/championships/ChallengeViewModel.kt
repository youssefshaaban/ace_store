package com.example.mvvm_template.ui.component.main.championships

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestGetChallangeDto
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.challenge.GetChallengeByIdUseCase
import com.example.mvvm_template.domain.interactor.challenge.GetChallengessUseCase
import com.example.mvvm_template.utils.FIRST_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val getChallengessUseCase: GetChallengessUseCase,
    private val getChallengeByIdUseCase: GetChallengeByIdUseCase
) : ViewModel() {
    private val _challengesPrivateLive = MutableLiveData<ArrayList<Challenge>>()
    val challengesLiveData: LiveData<ArrayList<Challenge>> get() = _challengesPrivateLive
    private val _challengePrivateLive = MutableLiveData<Challenge?>()
    val challengeLiveData: LiveData<Challenge?> get() = _challengePrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive
    var stopLoadingMore = false
    fun getChallenges(pageIndex: Int, status: Int) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            _loaderVisibilityLive.value = true
            getChallengessUseCase.execute(
                RequestGetChallangeDto(
                    PageIndex = pageIndex,
                    Status = status
                )
            ).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    if (it.data.size < 20) {
                        stopLoadingMore = true
                    }
                    if (pageIndex == FIRST_PAGE)
                        _challengesPrivateLive.value = ArrayList(it.data)
                    else {
                        _challengesPrivateLive.value?.addAll(it.data)
                        _challengesPrivateLive.value = _challengesPrivateLive.value
                    }
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }

    fun getChallengeById(id: Int) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            _loaderVisibilityLive.value = true
            getChallengeByIdUseCase.execute(id).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _challengePrivateLive.value = it.data
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }

}