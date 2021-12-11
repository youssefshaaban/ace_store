package com.example.mvvm_template.data.repositery

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.remote_service.BaseDataSource
import com.example.mvvm_template.domain.repository.PaymentRepo
import kotlinx.coroutines.delay
import toAmazonSdk
import java.util.*
import javax.inject.Inject

class PaymentRepoImp @Inject constructor() : PaymentRepo,
    BaseDataSource() {

}