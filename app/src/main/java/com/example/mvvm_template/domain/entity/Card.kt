package com.example.mvvm_template.domain.entity

data class Card(
    val children: List<String>? = null,
    val name: String,
    val id: Int,
) {

}