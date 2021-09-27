package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.repository.CategoryRepository
import javax.inject.Inject

class RepoCategoryImp @Inject constructor(private val categoryApi: CategoryApi):CategoryRepository,BaseDataSource() {
    val mapCategoryReponseReponseToCard=MapCategoryReponseReponseToCard()
    val mapCategoryReponseReponseToCategory=MapCategoryReponseReponseToCategory()
    override suspend fun getCards(): DataState<List<Card>> {
        val result=getResult {
            categoryApi.getCategoriesWithChildren()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { t->mapCategoryReponseReponseToCard.map(t) })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getCategories(
        pageNumber: Int,
        SubCategories: Boolean
    ): DataState<List<Category>> {
        val result=getResult {
            categoryApi.getCategories(pageNumber,SubCategories)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { t->mapCategoryReponseReponseToCategory.map(t) })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }
}