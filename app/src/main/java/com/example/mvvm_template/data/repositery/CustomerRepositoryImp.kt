package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.ApiService
import com.example.mvvm_template.data.remote_service.api.CustomerApi
import com.example.mvvm_template.data.remote_service.response.customer.toMemberType
import com.example.mvvm_template.data.remote_service.response.customer.toPoint
import com.example.mvvm_template.data.remote_service.response.customer.toWallet
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.entity.MemberType
import com.example.mvvm_template.domain.entity.Point
import com.example.mvvm_template.domain.entity.Wallet
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.repository.CustomerRepo
import javax.inject.Inject

class CustomerRepositoryImp @Inject constructor(private val apiService: CustomerApi) : CustomerRepo,
    BaseDataSource() {


    override suspend fun getPoints(): DataState<Point> {
        val result = getResult {
            apiService.getPoints()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toPoint())
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }

    override suspend fun getMemberShip(): DataState<List<MemberType>> {
        val result = getResult {
            apiService.getMemberType()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { t->t.toMemberType() })
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }

    override suspend fun getWallet(): DataState<Wallet?> {
        val result = getResult {
            apiService.getWallet()
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result?.toWallet())
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }

    override suspend fun chargeWallet(requestChargeWallet: RequestChargeWallet): DataState<Wallet?> {
        val result = getResult {
            apiService.chargeWallet(requestChargeWallet)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result?.toWallet())
            }
            is DataState.Error -> {
                DataState.Error(result.error)
            }
            else -> DataState.Error(Failure.UnknownError("some thing wrong"))
        }
    }
}