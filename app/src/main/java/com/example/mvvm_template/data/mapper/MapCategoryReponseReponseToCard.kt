package com.example.mvvm_template.data.mapper

import com.example.mvvm_template.data.remote_service.response.ProfileResponse
import com.example.mvvm_template.data.remote_service.response.category.CategoryResponse
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.Profile

class MapCategoryReponseReponseToCard:BaseMapper<CategoryResponse,Card> {


    override fun map(t: CategoryResponse): Card {
        return Card(
            name = t.name,
            children = t.children?.map { Category(children = emptyList(),description =t.description,id = t.id,imagePath = t.imagePath,name = t.name) },
            id =  t.id,
            iconPath = t.imagePath
        )
    }
}