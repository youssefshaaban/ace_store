package com.example.mvvm_template.data.remote_service.response

import com.example.mvvm_template.domain.entity.HomeData
import com.example.mvvm_template.domain.entity.Slider

data class SliderResponse(
    val description: String,
    val destinationUrl: String,
    val imagePath: String,
    val itemId: Int,
    val slideDestination: Int,
    val title: String
)


fun SliderResponse.toSlider(): Slider {
    return Slider(destinationUrl, imagePath, itemId, slideDestination)
}

data class CartAndSliderResponse(
    val cartProductsCount: Int,
    val result: List<SliderResponse>
)

fun CartAndSliderResponse.toHomeData(): HomeData {
    return HomeData(this.result.map { sli -> sli.toSlider() }, this.cartProductsCount)
}
