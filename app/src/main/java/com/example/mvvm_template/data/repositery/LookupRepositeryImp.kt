package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.ApiFiles
import com.example.mvvm_template.data.remote_service.api.LookupApi
import com.example.mvvm_template.data.remote_service.response.toCountry
import com.example.mvvm_template.domain.entity.Country
import com.example.mvvm_template.domain.repository.FileUploadRepo
import com.example.mvvm_template.domain.repository.LookupRepository
import com.example.mvvm_template.utils.FileUtils2
import okhttp3.MediaType

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class LookupRepositeryImp @Inject constructor(private val lookupApi: LookupApi) : LookupRepository,
    BaseDataSource() {
    override suspend fun getCountries(): DataState<List<Country>> {
        val result = getResult {
            lookupApi.getCountries()
        }
        return if (result is DataState.Success)
            DataState.Success(result.data.result.map { ma -> ma.toCountry() })
        else
            DataState.Error((result as DataState.Error).error)
    }

}