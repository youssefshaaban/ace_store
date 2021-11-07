package com.example.mvvm_template.domain.repository

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Resource

interface ResourcesRepo {
    suspend fun getAbout():DataState<Resource>
    suspend fun getRefundPolicy():DataState<Resource>
    suspend fun getFAQ():DataState<List<Resource>>
}