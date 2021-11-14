package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.data.remote_service.api.ProductsApi
import com.example.mvvm_template.data.remote_service.api.ResourcesApi
import com.example.mvvm_template.data.remote_service.response.product.toProductModel
import com.example.mvvm_template.data.remote_service.response.toHomeData
import com.example.mvvm_template.data.remote_service.response.toResource
import com.example.mvvm_template.data.remote_service.response.toSlider
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.*
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import com.example.mvvm_template.domain.repository.ResourcesRepo
import javax.inject.Inject

class RepoResourcesImp @Inject constructor(private val resourcesApi: ResourcesApi):ResourcesRepo,BaseDataSource() {
    override suspend fun getAbout(): DataState<Resource> {
        val result=getResult {
            resourcesApi.getAbout()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toResource())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getRefundPolicy(): DataState<Resource> {
        val result=getResult {
            resourcesApi.getRefundPolicy()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toResource())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getFAQ(): DataState<List<Resource>> {
        val result=getResult {
            resourcesApi.getFAQS()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { re->re.toResource() })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getSlider(): DataState<HomeData> {
        val result=getResult {
            resourcesApi.getSlider()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.toHomeData())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

}