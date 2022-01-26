package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCard
import com.example.mvvm_template.data.mapper.MapCategoryReponseReponseToCategory
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.data.remote_service.api.CategoryApi
import com.example.mvvm_template.data.remote_service.api.ChallengeApi
import com.example.mvvm_template.data.remote_service.api.ProductsApi
import com.example.mvvm_template.data.remote_service.api.ResourcesApi
import com.example.mvvm_template.data.remote_service.response.challange.toChallengeEntity
import com.example.mvvm_template.data.remote_service.response.product.toProductModel
import com.example.mvvm_template.data.remote_service.response.toHomeData
import com.example.mvvm_template.data.remote_service.response.toResource
import com.example.mvvm_template.data.remote_service.response.toSlider
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import com.example.mvvm_template.domain.dto.RequestGetChallangeDto
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.*
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.ChallengeRepo
import com.example.mvvm_template.domain.repository.ProductRepository
import com.example.mvvm_template.domain.repository.ResourcesRepo
import javax.inject.Inject

class RepoChallengeImp @Inject constructor(private val challengeApi: ChallengeApi):ChallengeRepo,BaseDataSource() {
    override suspend fun getChallengeById(id: Int): DataState<Challenge?> {
        val result=getResult {
            challengeApi.getChallengesById(id)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.toChallengeEntity())
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun getChallenges(requestGetChallangeDto: RequestGetChallangeDto): DataState<List<Challenge>> {
        val result=getResult {
            challengeApi.getChallenges(requestGetChallangeDto.toMap())
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(result.data.result.map { t->t.toChallengeEntity() })
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

    override suspend fun submitChallenge(requestChallengeDTo: RequestChallengeDTo): DataState<Boolean> {
        val result=getResult {
            challengeApi.joinChallenge(requestChallengeDTo)
        }
        return when (result) {
            is DataState.Success -> {
                DataState.Success(true)
            }
            else -> {
                DataState.Error((result as DataState.Error).error)
            }
        }
    }

}