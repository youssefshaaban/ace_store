package com.example.mvvm_template.domain.entity

data class Category(
    val children: List<String>? = null,
    val description: String?=null,
    val id: Int?=null,
    val imagePath: String?=null,
    val name: String?=null
)

