package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState

import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GenerateOtpUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<GenerateOtpUseCase.RequestOTP,DataState<String> > {
    override fun execute(param: RequestOTP): Flow<DataState<String>> {
        return flow {
            emit(accountRepository.generateOtp(param))
        }.flowOn(ioDispatcher)
    }

    data class RequestOTP(
        val countryCode: String,
        val mobileNumber: String
    )


}