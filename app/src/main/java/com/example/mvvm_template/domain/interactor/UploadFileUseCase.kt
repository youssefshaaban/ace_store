package com.example.mvvm_template.domain.interactor

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.repository.FileUploadRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UploadFileUseCase @Inject constructor(private val fileUploadRepo: FileUploadRepo,private val ioDispatcher: CoroutineContext):UseCase<List<File>,DataState<List<String>>> {
    override fun execute(param: List<File>): Flow<DataState<List<String>>> {
        return flow {
            emit(fileUploadRepo.uploadFile(param))
        }.flowOn(ioDispatcher)
    }
}