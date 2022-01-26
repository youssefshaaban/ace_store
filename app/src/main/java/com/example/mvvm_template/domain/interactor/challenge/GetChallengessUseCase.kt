package com.example.mvvm_template.domain.interactor.challenge

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.repositery.RepoChallengeImp
import com.example.mvvm_template.domain.dto.RequestChallengeDTo
import com.example.mvvm_template.domain.dto.RequestGetChallangeDto
import com.example.mvvm_template.domain.dto.RequestGetProductDto
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Challenge
import com.example.mvvm_template.domain.entity.Product
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetChallengessUseCase @Inject constructor(private val repoChallengeImp: RepoChallengeImp, private val ioDispatcher: CoroutineContext):UseCase<RequestGetChallangeDto,DataState<List<Challenge>>> {
    override fun execute(param: RequestGetChallangeDto): Flow<DataState<List<Challenge>>> {
        return flow {
            emit(repoChallengeImp.getChallenges(param))
        }.flowOn(ioDispatcher)
    }
}