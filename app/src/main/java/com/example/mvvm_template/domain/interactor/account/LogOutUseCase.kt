package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LogOutUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext)
    : UseCase<Nothing, DataState<Boolean>> {
    override fun execute(param: Nothing?): Flow<DataState<Boolean>> {
        return  flow {
            emit(accountRepository.logOut())
        }.flowOn(ioDispatcher)
    }

}