package com.example.mvvm_template.data.remote_service.response.product

data class ProductResponse(
    val currency: Currency,
    val descriptionShort: String,
    val id: Int,
    val imagePath: String,
    val metaDescription: String,
    val name: String,
    val price: Int,
    val priceAfterDiscount: Int
)


fun ProductResponse.toProductModel(): com.example.mvvm_template.domain.entity.Product {
    return com.example.mvvm_template.domain.entity.Product(
        currency.toCurrencyModel(),
        descriptionShort,
        id,
        imagePath,
        metaDescription,
        name,
        price,
        priceAfterDiscount,
        images = emptyList()
    )
}