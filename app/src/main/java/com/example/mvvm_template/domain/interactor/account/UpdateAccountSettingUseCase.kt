package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.FieldsFormValidation
import com.example.mvvm_template.domain.dto.RequestUpdateSetting
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.utils.RegexUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Throws

class UpdateAccountSettingUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<RequestUpdateSetting,DataState<Boolean> > {

    @Throws(NullCodeByMobileNumber::class,NullCodeByEmail::class)
    override fun execute(param: RequestUpdateSetting): Flow<DataState<Boolean>> {
        if (param.sendCardCodeByEmail==null){
            throw NullCodeByEmail()
        }else if (param.sendCardCodeByMobileNumber==null){
            throw NullCodeByMobileNumber()
        }
        return flow {
            emit(accountRepository.updateAccountSetting(param))
        }.flowOn(ioDispatcher)
    }


    class NullCodeByEmail :Throwable()
    class NullCodeByMobileNumber :Throwable()

}