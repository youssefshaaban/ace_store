package com.example.mvvm_template.domain.interactor

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.response.LoginResponse
import com.example.mvvm_template.domain.dto.RequestOTP
import com.example.mvvm_template.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GenerateOtpUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext) {
//    suspend fun login(requestOTP: RequestOTP):Flow<DataState<LoginResponse>>{
//        if (requestOTP.mobileNumber.isEmpty())
//       // return accountRepository.generateOtp(requestOTP).flowOn(ioDispatcher)
//    }
}