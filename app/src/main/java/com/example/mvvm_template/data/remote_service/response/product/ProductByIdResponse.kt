package com.example.mvvm_template.data.remote_service.response.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ProductByIdResponse(
    val currency: Currency,
    val description: String,
    val descriptionShort: String,
    val id: Int,
    val imagePath: String,
    val images: List<Image>,
    val metaDescription: String,
    val name: String,
    val price: Double,
    val priceAfterDiscount: Double,
    val rate: Rate?=null,
    var isAtCart:Boolean=false,
    val productType:Int
)

@Parcelize
data class Currency(
    val conversionRate: Int,
    val id: Int,
    val name: String,
    val symbol: String
):Parcelable

data class Image(
    val caption: String,
    val imagePath: String,
    val isCover: Boolean,
    val position: Int
)

data class Rate(
    val grade1Percentage: Int,
    val grade2Percentage: Int,
    val grade3Percentage: Int,
    val grade4Percentage: Int,
    val grade5Percentage: Int,
    val reviews: List<Review>?=null,
    val totalReviews: Int,
    val totalReviewsPercentage: Int
)

data class Review(
    val customerId: Int,
    val customerName: String,
    val customerReview: String,
    val dateAdd: String,
    val grade: Int,
    val title: String
)

fun Currency.toCurrencyModel(): com.example.mvvm_template.domain.entity.Currency {
    return com.example.mvvm_template.domain.entity.Currency(
        conversionRate = this.conversionRate,
        name=this.name,
        id = this.id, symbol = this.symbol
    )
}

fun Review.toReviewModel(): com.example.mvvm_template.domain.entity.Review {
    return com.example.mvvm_template.domain.entity.Review(
        this.customerName, this.customerReview, this.dateAdd, this.grade, this.title
        ,customerId=this.customerId
    )
}

fun ProductByIdResponse.toProductModel(): com.example.mvvm_template.domain.entity.Product {
    return com.example.mvvm_template.domain.entity.Product(
        currency.toCurrencyModel(),
        descriptionShort,
        id = id,
        imagePath = imagePath,
        metaDescription=metaDescription,
        name = name,
        price = price,
        isAtCart = isAtCart,
        productType=this.productType,
        priceAfterDiscount = priceAfterDiscount,
        images = images.map { im -> im.imagePath }
        ,reviews = this.rate?.reviews?.map { re->re.toReviewModel() }
    )
}