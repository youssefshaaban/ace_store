package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState

import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetProfileUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<Unit,DataState<Profile> > {
    override fun execute(param: Unit): Flow<DataState<Profile>> {
        return flow {
            emit(accountRepository.getProfile())
        }.flowOn(ioDispatcher)
    }


}