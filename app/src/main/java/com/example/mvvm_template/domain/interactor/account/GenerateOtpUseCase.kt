package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.ValidationPhone

import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.utils.RegexUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GenerateOtpUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<GenerateOtpUseCase.RequestOTP,DataState<Boolean> > {
    override fun execute(param: RequestOTP): Flow<DataState<Boolean>> {
        if (validate(param!!)!=null){
            return flow {
                emit(DataState.Validation(validate(param)!!))
            }
        }else{
            return flow {
                emit(accountRepository.generateOtp(param))
            }.flowOn(ioDispatcher)
        }
    }

    data class RequestOTP(
        val countryCode: String,
        val mobileNumber: String
    )

    fun validate(requestOTP: RequestOTP):ValidationPhone?{
        return if (requestOTP.mobileNumber.isEmpty()){
            ValidationPhone.EMPTY_PHONE
        }else if (RegexUtils.isValidPhoneNumber("966",requestOTP.mobileNumber)){
            ValidationPhone.INVALID_PHONE
        }
        else null
    }

}