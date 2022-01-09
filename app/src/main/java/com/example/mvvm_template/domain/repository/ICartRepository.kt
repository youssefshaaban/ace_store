package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product

interface ICartRepository {
    suspend fun addToCart(requestAddCart: RequestAddCart):DataState<Cart>
    suspend fun getCarts():DataState<Cart?>
}