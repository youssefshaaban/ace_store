package com.example.mvvm_template.data.remote_service.response

data class BaseReponse<T>(val result:T)
data class BaseReponseArray<T>(val result:List<T>)
data class BaseReponseArrayPagination<T>(
    val result:List<T>,
    val pageSize:Int?=null,
    val pageIndex:Int?=null,
    )