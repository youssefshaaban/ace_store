package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.entity.MemberType
import com.example.mvvm_template.domain.entity.Point
import com.example.mvvm_template.domain.entity.Wallet
import java.io.File

interface CustomerRepo {
    suspend fun getPoints(): DataState<Point>
    suspend fun getMemberShip(): DataState<List<MemberType>>
    suspend fun getWallet(): DataState<Wallet?>
    suspend fun chargeWallet(requestChargeWallet: RequestChargeWallet): DataState<Wallet?>
}