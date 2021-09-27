package com.example.mvvm_template.data.mapper

import com.example.mvvm_template.data.remote_service.response.ProfileResponse
import com.example.mvvm_template.data.remote_service.response.category.CategoryResponse
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.Profile

class MapCategoryReponseReponseToCategory:BaseMapper<CategoryResponse,Category> {


    override fun map(t: CategoryResponse): Category {
        return Category(
            name = t.name,
            children = t.children,
            id =  t.id,
            description = t.description,
            imagePath = t.imagePath,
        )
    }
}