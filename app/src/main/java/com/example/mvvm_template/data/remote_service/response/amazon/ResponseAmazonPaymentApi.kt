import com.example.mvvm_template.domain.entity.AmazonSdk
import java.io.Serializable

data class ResponseAmazonPaymentApi(
    val language:String ?= null,
    val device_id:String ?= null,
    val service_command:String ?= null,
    val signature:String ?= null,
    val access_code:String ?= null,
    val merchant_identifier:String ?= null,
    val sdk_token:String ?= null,
    val response_message:String ?= null,
    val response_code:Int ?= null,
    val status:Int?= null,
    val merchant_reference :String?= null ,
    val customer_ip :String ?= null

): Serializable

fun ResponseAmazonPaymentApi.toAmazonSdk():AmazonSdk =AmazonSdk(this.signature,this.access_code,this.merchant_identifier,this.sdk_token)