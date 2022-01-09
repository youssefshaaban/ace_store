package com.example.mvvm_template.domain.dto

class RequestGetProductDto(
    val PageSize: Int = 20,
    val PageIndex: Int = 1, val SearchText: String? = null,
    val CategoryId: String? = null,
    val BestSell: Boolean? = null,
    val HasOffer: Boolean? = null
) {
    fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        CategoryId?.let {
            map.put("CategoryId", CategoryId)
        }
        SearchText?.let {
            map.put("SearchText", SearchText)
        }
        BestSell?.let {
            map["BestSell"] = BestSell.toString()
        }
        HasOffer?.let {
            map["HasOffer"] = HasOffer.toString()
        }
        map["PageIndex"] = PageIndex.toString()
        map["PageSize"] = PageSize.toString()
        return map
    }
}