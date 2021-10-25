package com.example.mvvm_template.domain.interactor.lookup

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.FileUploadRepo
import com.example.mvvm_template.domain.repository.LookupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetCountryUseCase @Inject constructor(private val lookupRepository: LookupRepository, private val ioDispatcher: CoroutineContext):
    UseCase<Unit, DataState<List<Country>>> {
    override fun execute(param: Unit): Flow<DataState<List<Country>>> {
        return flow {
            emit(lookupRepository.getCountries())
        }.flowOn(ioDispatcher)
    }

}