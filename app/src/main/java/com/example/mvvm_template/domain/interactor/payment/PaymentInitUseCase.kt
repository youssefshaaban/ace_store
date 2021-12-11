package com.example.mvvm_template.domain.interactor.payment

import com.example.mvvm_template.App
import com.example.mvvm_template.BuildConfig
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.domain.dto.RequestPaymentApi
import com.example.mvvm_template.domain.entity.AmazonSdk
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.repository.PaymentRepo

import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

class PaymentInitUseCase @Inject constructor (private val paymentRepo: PaymentRepo,private val appPrefrances: AppPrefrances) {
}