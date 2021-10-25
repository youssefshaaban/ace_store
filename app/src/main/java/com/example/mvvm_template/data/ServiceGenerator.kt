package com.example.mvvm_template.data

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.mvvm_template.App
import com.example.mvvm_template.core.common.BASE_URL
import com.example.mvvm_template.utils.ConstantMethod
import com.example.mvvm_template.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val timeoutRead = 30   //In seconds
private const val contentType = "Content-Type"
private const val accept_language = "Accept-Language"
private const val authrization = "Authorization"
private const val contentTypeValue = "application/json"
private const val timeoutConnect = 30   //In seconds
private const val academyId = "AcademyId"
private const val academyIdValue = "1"
class ServiceGenerator (private val device:String) {
    val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    // Create the Collector
    val chuckerCollector = ChuckerCollector(
        context = App.context,
        // Toggles visibility of the push notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    // Create the Interceptor
    val chuckerInterceptor = ChuckerInterceptor.Builder(App.context)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // List of headers to replace with ** in the Chucker UI
        .redactHeaders("Auth-Token", "Bearer")
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        .build()
    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(contentType, contentTypeValue)
        App.getUser()?.let {
            requestBuilder.addHeader(authrization,"Bearer ${it.token}")
        }
        requestBuilder.addHeader("DeviceId",device)
        requestBuilder.addHeader("DeviceType","Android")
        requestBuilder.addHeader("LanguageId","1")
        val request = requestBuilder.build()
        chain.proceed(request)
    }


//    private val logger: HttpLoggingInterceptor
//        get() {
//            val loggingInterceptor = HttpLoggingInterceptor()
////            if (BuildConfig.DEBUG) {
////                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
////            }
//            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
//            return loggingInterceptor
//        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)

        okHttpBuilder.addInterceptor(chuckerInterceptor)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }



}
