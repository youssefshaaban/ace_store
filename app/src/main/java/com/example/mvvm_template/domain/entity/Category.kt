package com.example.mvvm_template.domain.entity

data class Category(
    val children: List<String>? = null,
    val description: String,
    val id: Int,
    val imagePath: String,
    val name: String
)

