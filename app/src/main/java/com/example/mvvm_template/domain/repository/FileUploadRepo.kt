package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import java.io.File

interface FileUploadRepo {
    suspend fun uploadFile(list: List<File>): DataState<List<String>>
}