package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.dto.RequestGetChallangeDto
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.domain.entity.MemberType
import com.example.mvvm_template.domain.entity.Point
import com.example.mvvm_template.domain.entity.Wallet
import java.io.File

interface ChallengeRepo {
    suspend fun getChallengeById(id:Int): DataState<Challenge?>
    suspend fun getChallenges(requestGetChallangeDto: RequestGetChallangeDto): DataState<List<Challenge>>
    suspend fun submitChallenge(requestChallengeDTo: RequestChallengeDTo): DataState<Boolean>
}