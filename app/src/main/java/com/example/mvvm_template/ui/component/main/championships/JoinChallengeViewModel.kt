package com.example.mvvm_template.ui.component.main.championships

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RateDTO
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import com.example.mvvm_template.domain.dto.RequestGetChallangeDto
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.challenge.GetChallengeByIdUseCase
import com.example.mvvm_template.domain.interactor.challenge.GetChallengessUseCase
import com.example.mvvm_template.domain.interactor.challenge.JoinChallengesUseCase
import com.example.mvvm_template.utils.FIRST_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinChallengeViewModel @Inject constructor(
private val joinChallengesUseCase: JoinChallengesUseCase
) : ViewModel() {


    private val _successJoinPrivateLive = MutableLiveData<Boolean>()
    val successLiveData: LiveData<Boolean> get() = _successJoinPrivateLive
    private val _failurePrivateLive = MutableLiveData<Failure>()
    val faluireLiveData: LiveData<Failure> get() = _failurePrivateLive
    private val _loaderVisibilityLive = MutableLiveData<Boolean>()
    val loaderVisibilityLiveData: LiveData<Boolean> get() = _loaderVisibilityLive

    fun joinChallenge(requestChallengeDTo: RequestChallengeDTo) {
        _loaderVisibilityLive.value = true
        viewModelScope.launch {
            _loaderVisibilityLive.value = true
            joinChallengesUseCase.execute(requestChallengeDTo).collect {
                _loaderVisibilityLive.value = false
                if (it is DataState.Success) {
                    _successJoinPrivateLive.value=true
                } else if (it is DataState.Error) {
                    _failurePrivateLive.value = it.error
                }
            }
        }
    }

}