package com.example.mvvm_template.domain.entity

data class Currency(
    val conversionRate: Int? = null,
    val id: Int,
    val name: String? = null
) {
}