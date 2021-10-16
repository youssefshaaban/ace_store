package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CartApi
import com.example.mvvm_template.data.remote_service.response.cart.toCart
import com.example.mvvm_template.data.remote_service.response.cart.toProduct
import com.example.mvvm_template.domain.dto.RequestAddCart
import com.example.mvvm_template.domain.entity.Cart
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.repository.ICartRepository
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(private val cartApi: CartApi) : ICartRepository,
    BaseDataSource() {
    override suspend fun addToCart(requestAddCart: RequestAddCart): DataState<Cart> {
        val result = getResult {
            cartApi.addCart(requestAddCart)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toCart())
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }

    override suspend fun getCarts(): DataState<Cart> {
        val result = getResult {
            cartApi.getCart()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toCart())
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }
}