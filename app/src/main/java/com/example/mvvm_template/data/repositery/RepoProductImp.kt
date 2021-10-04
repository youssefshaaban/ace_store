package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.data.remote_service.api.ProductsApi
import com.example.mvvm_template.data.remote_service.response.product.toProductModel
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import javax.inject.Inject

class RepoProductImp @Inject constructor(private val productsApi: ProductsApi):ProductRepository,BaseDataSource() {
    override suspend fun getProducts(requestGetProductDto: RequestGetProductDto): DataState<List<Product>> {
        val result=getResult {
            productsApi.getProductsPage(requestGetProductDto.toMap())
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { productResponse -> productResponse.toProductModel() })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }


    override suspend fun getProductById(id: Int): DataState<Product> {
        val result=getResult {
            productsApi.getProductById(id)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toProductModel())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

}