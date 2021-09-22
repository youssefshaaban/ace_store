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

class UpdateProfileUseCase @Inject constructor(private val accountRepository: AccountRepository, private val ioDispatcher: CoroutineContext):
    UseCase<UpdateProfileUseCase.UpdateRequestProfile,DataState<Profile> > {
    override fun execute(param: UpdateRequestProfile): Flow<DataState<Profile>> {
        return flow {
            emit(accountRepository.updateProfile(param!!))
        }.flowOn(ioDispatcher)
    }

    data class UpdateRequestProfile(
        val email: String,
        val firstName: String,
        val imageName: String?=null,
        val lastName: String,
        val token:String
    )
}