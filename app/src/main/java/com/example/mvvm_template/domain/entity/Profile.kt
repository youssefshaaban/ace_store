package com.example.mvvm_template.domain.entity

data class Profile(
    val email: String?=null,
    val id: String?=null,
    val imagePath: String?=null,
    val imageName: String?=null,
    val mobileNumber: String?=null,
    val name: String?=null,
    val firstName: String?=null,
    val secondName: String?=null,
    val sendCardCodeByEmail:Boolean?=null,
    val sendCardCodeByMobileNumber:Boolean?=null,
    val memberType: MemberType?=null,
    val point: Point?=null
)