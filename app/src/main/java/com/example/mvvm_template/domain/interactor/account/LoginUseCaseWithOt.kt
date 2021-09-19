package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.entity.ValidationPhone
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.utils.RegexUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginUseCaseWithOt @Inject constructor(
    private val accountRepository: AccountRepository,
    private val ioDispatcher: CoroutineContext
) : UseCase<LoginUseCaseWithOt.RequestLogin, DataState<User>> {
    override fun execute(param: RequestLogin): Flow<DataState<User>> {
        val validate=validate(param!!)
        return if (validate!=null){
            flow {
                emit(DataState.Validation(validate))
            }
        }else{
            flow {
                emit(accountRepository.login(param))
            }.flowOn(ioDispatcher)
        }
    }


    data class RequestLogin(
        val grantType: String="otp",
        val otp: String?,
        val password: String?=null,
        val refreshToken: String?=null,
        val userName: String?
    )

    fun validate(requestLogin: RequestLogin):ValidationPhone?{
        return if (requestLogin.userName.isNullOrEmpty()){
            ValidationPhone.EMPTY_PHONE
        }else if (RegexUtils.isValidPhoneNumber("966",requestLogin.userName)){
            ValidationPhone.INVALID_PHONE
        }else if (requestLogin.otp.isNullOrEmpty()){
            ValidationPhone.EMPTY_OTP
        }else null
    }


}