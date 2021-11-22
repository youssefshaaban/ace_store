package com.example.mvvm_template.domain.interactor.payment

import com.example.mvvm_template.App
import com.example.mvvm_template.BuildConfig
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.domain.dto.RequestPaymentApi
import com.example.mvvm_template.domain.entity.AmazonSdk
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.repository.PaymentRepo
import com.payfort.fortpaymentsdk.domain.model.FortRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

class PaymentInitUseCase @Inject constructor (private val paymentRepo: PaymentRepo,private val appPrefrances: AppPrefrances, @Named("deviceIdFort")val deviceId:String) {
     val SHA_TYPE = "SHA-256"

    @Throws()
   suspend fun getFortRequest(amount:Double,currency:String):Flow<FortRequest>{
        val result =paymentRepo.getMerchantRef()
       val request=RequestPaymentApi(access_code = BuildConfig.ACCESS_CODE_PAYFORT,device_id = deviceId,signature = createSignature(),merchant_identifier = BuildConfig.MERCHANT_ID,language = if (appPrefrances.getLang()=="ar") "ar" else
           "en")
       val sdToken=paymentRepo.getSDKToken(request)
       if (sdToken is DataState.Success){
           return flow {
             emit(  FortRequest().apply { requestMap=getMapRequest(amount=amount,currency=currency,sdkToken = sdToken.data.sdk_token!!,result.result) })
           }.flowOn(Dispatchers.IO)
       }else{
           throw SDKTokenException((sdToken as DataState.Error).error)
       }
    }

    private val KEY_MERCHANT_IDENTIFIER = "merchant_identifier"
    private val KEY_SERVICE_COMMAND = "service_command"
    private val KEY_LANGUAGE = "language"
    private val KEY_ACCESS_CODE = "access_code"
    private val LANGUAGE_TYPE = "en"
    private fun getMapRequest(amount:Double,currency: String,sdkToken:String,merchantReference:String): MutableMap<String, Any>? {
        val paymentMethods= arrayOf("VISA", "MASTERCARD", "AMEX", "MADA","MEEZA")
        val requestMap:HashMap<String, Any> = HashMap()
        requestMap.put("command", "PURCHASE")
        requestMap.put("customer_email", "shimaasamir54@gmail.com")
        requestMap.put("currency", currency)
        requestMap.put("amount", amount)
        requestMap.put("language", appPrefrances.getLang())
        requestMap.put("merchant_reference", merchantReference)
        requestMap.put("customer_name", App.getUser()!!.name!!)
//        requestMap.put("payment_option", "VISA")
        requestMap.put("order_description", "DESCRIPTION")
        requestMap.put("eci", "ECOMMERCE")
        requestMap.put("sdk_token", sdkToken)
        return requestMap
    }

    private fun createSignature(): String? {
        val concatenatedString = BuildConfig.SHA_Request_Phrase +
                KEY_ACCESS_CODE + "=" + BuildConfig.ACCESS_CODE_PAYFORT +
                KEY_LANGUAGE + "=" + appPrefrances.getLang() +
                KEY_MERCHANT_IDENTIFIER + "=" + BuildConfig.MERCHANT_ID +
                KEY_SERVICE_COMMAND + "=" + "SDK_TOKEN" +
                BuildConfig.SHA_Request_Phrase
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest.getInstance(SHA_TYPE)
            digest.update(concatenatedString.toByteArray())
            val messageDigest: ByteArray = digest.digest()
            return java.lang.String.format(
                "%0" + messageDigest.size * 2 + 'x',
                BigInteger(1, messageDigest)
            )
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }


    class SDKTokenException(val error: Failure):Throwable()
}