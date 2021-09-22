package com.example.mvvm_template.domain.interactor.category

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.interactor.UseCase
import kotlinx.coroutines.flow.Flow

class GetCardUseCase:UseCase<Unit,DataState<Card>> {
    override fun execute(param: Unit?): Flow<DataState<Card>> {

    }
}