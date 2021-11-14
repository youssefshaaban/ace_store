package com.example.mvvm_template.domain.entity

data class Slider(
    val destinationUrl: String?=null,
    val imagePath: String?=null,
    val itemId: Int?=null,
    val slideDestination: Int?=null,
)

class HomeData(val sliders:List<Slider>,
                val cartCount:Int
               )