package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.ApiFiles
import com.example.mvvm_template.domain.repository.FileUploadRepo
import com.example.mvvm_template.utils.FileUtils2
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody


import java.io.File
import javax.inject.Inject


class FileUploadImp @Inject constructor(private val apiFiles: ApiFiles):FileUploadRepo,BaseDataSource() {
    override suspend fun uploadFile(list: List<File>) : DataState<List<String>> {
       return getResult {
            apiFiles.uploadFiles(getParts(list))
        }
    }

    private fun createRequestForFile(
        partName: String,
        file: File,
        fileType: String
    ): MultipartBody.Part {
        // create RequestBody instance from file
//        val requestFile = RequestBody.create(
//            fileType,
//            file
//        )
       val request= file.asRequestBody(fileType.toMediaTypeOrNull())
        // MultipartBody.Part is used to send also the actual file name
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.name,request)
    }

    fun getParts(list: List<File>):ArrayList<MultipartBody.Part>{
        val parts=ArrayList<MultipartBody.Part>()
        list.forEach {
            parts.add(createRequestForFile(partName = it.name,file = it,fileType = FileUtils2.getMimeType(it)))
        }
        return parts
    }
}