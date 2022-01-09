package com.example.mvvm_template.domain.interactor.cutomer

import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.dto.RequestChargeWallet
import com.example.mvvm_template.domain.entity.Wallet
import com.example.mvvm_template.domain.interactor.UseCase
import com.example.mvvm_template.domain.repository.CustomerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Throws

class ChargeWalletUseCase @Inject constructor(
    private val customerRepo: CustomerRepo,
    private val ioDispatcher: CoroutineContext
) :
    UseCase<RequestChargeWallet, DataState<Wallet?>> {
    @Throws(
        PaymentMethodIdNotFoundException::class,
        MissingException::class,
        CryptoDataMissingException::class,
        AmountMissingException::class
    )
    override fun execute(param: RequestChargeWallet): Flow<DataState<Wallet?>> {
        if (validation(param)) {
            return flow {
                emit(customerRepo.chargeWallet(requestChargeWallet = param))
            }.flowOn(ioDispatcher)
        } else {
            throw MissingException()
        }
    }

    private fun validation(requestChargeWallet: RequestChargeWallet): Boolean {
        if (requestChargeWallet.amount == null || requestChargeWallet.amount == 0.0) {
            throw AmountMissingException()
        } else if (requestChargeWallet.cryptedData.isNullOrEmpty()) {
            throw CryptoDataMissingException()
        } else if (requestChargeWallet.paymentMethodId == null || requestChargeWallet.paymentMethodId == 0) {
            throw CryptoDataMissingException()
        }
        return true
    }

    class PaymentMethodIdNotFoundException(override val message: String?="PaymentMethodIdNotFoundException") : Throwable()
    class CryptoDataMissingException(override val message: String?="CryptoDataMissingException") : Throwable()
    class AmountMissingException(override val message: String?="AmountMissingException") : Throwable()
    class MissingException : Throwable()

}