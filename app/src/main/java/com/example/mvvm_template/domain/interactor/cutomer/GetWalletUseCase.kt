package com.example.mvvm_template.domain.interactor.cutomer

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.domain.entity.Point
import com.example.mvvm_template.domain.entity.Wallet
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CustomerRepo
import com.example.mvvm_template.domain.repository.FileUploadRepo
import com.example.mvvm_template.domain.repository.LookupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetWalletUseCase @Inject constructor(private val customerRepo: CustomerRepo, private val ioDispatcher: CoroutineContext):
    UseCase<Unit, DataState<Wallet?>> {
    override fun execute(param: Unit): Flow<DataState<Wallet?>> {
        return flow {
            emit(customerRepo.getWallet())
        }.flowOn(ioDispatcher)
    }

}