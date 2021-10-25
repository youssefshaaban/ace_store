package com.example.mvvm_template.domain.interactor.account

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.FieldsFormValidation
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

class UpdateProfileUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<UpdateProfileUseCase.UpdateRequestProfile,DataState<Profile> > {
    @Throws(UpdateProfileValidationException::class)
    override fun execute(param: UpdateRequestProfile): Flow<DataState<Profile>> {
        if (param.email.isEmpty())
            throw UpdateProfileValidationException(FieldsFormValidation.EMPTY_EMAIL.value)
        if (!RegexUtils.isValidEmail(param.email))
            throw UpdateProfileValidationException(FieldsFormValidation.INVALID_EMAIL.value)
        if (param.firstName.isEmpty())
            throw UpdateProfileValidationException(FieldsFormValidation.EMPTY_FIRSTNAME.value)
        if (param.lastName.isEmpty())
            throw UpdateProfileValidationException(FieldsFormValidation.EMPTY_LASTNAME.value)
        return flow {
            emit(accountRepository.updateProfile(param))
        }.flowOn(ioDispatcher)
    }

    data class UpdateRequestProfile(
        val email: String,
        val firstName: String,
        val imageName: String?=null,
        val lastName: String,
        val token:String?=null
    )
    class UpdateProfileValidationException( val validationType: Int) : Exception()
}