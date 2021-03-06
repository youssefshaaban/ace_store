package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Country

import com.example.mvvm_template.domain.entity.User
import com.example.mvvm_template.domain.entity.Profile
import com.example.mvvm_template.domain.interactor.account.GenerateOtpUseCase
import com.example.mvvm_template.domain.interactor.account.LoginUseCaseWithOt
import com.example.mvvm_template.domain.interactor.account.UpdateFirBaseTokenUseCase
import com.example.mvvm_template.domain.interactor.account.UpdateProfileUseCase
import retrofit2.http.*

interface LookupRepository {
    suspend fun getCountries(): DataState<List<Country>>
}