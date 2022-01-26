package com.example.mvvm_template.domain.dto

class RequestGetChallangeDto(
    val PageSize: Int = 20,
    val PageIndex: Int = 1,
    val Status: Int? = null,
) {
    fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        Status?.let {
            map.put("Status", Status.toString())
        }
        map["PageIndex"] = PageIndex.toString()
        map["PageSize"] = PageSize.toString()
        return map
    }
}