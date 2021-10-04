package com.example.mvvm_template.domain.entity

data class Card(
    val children: List<Category>? = null,
    val name: String?=null,
    val iconPath:String?=null,
    val id: Int,
) {

}