package com.example.mvvm_template.data.remote_service.response.category

data class CategoryResponse(
    val children: List<CategoryResponse>?=null,
    val description: String,
    val id: Int,
    val imagePath: String,
    val name: String
)