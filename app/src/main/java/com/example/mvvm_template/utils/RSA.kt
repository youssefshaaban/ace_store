package com.example.mvvm_template.utils

import android.util.Base64
import java.lang.Exception
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RSA {
  var PUBLIC_KEY = "MIIBIDANBgkqhkiG9w0BAQEFAAOCAQ0AMIIBCAKCAQEA21z7Vcrrraiksj31If5K f7XGv6QNoHP7SRPjxxbxAnPrrI597NI683pHIaIgb0UNaOUggU6FYN+w+tBc1Mwk 1aOBsM8Ok6W0SsFxpa+Jt3VdOfF4iBw7k4sdd+EP5PfaiFdbrndRcCmV32mb87+I cuzDRxyqgl1Bx0dCPqmw0YCCWTuM+LXN60MHr56M5WO7J64AXn5YVzspZkon4Leg d9QbycUC77e/MUmhZL5QcGvXaBYWS5Lw5ROhjMYrLK15f4gWoYLtDcUTtMEEEtef EF4tus0Vx7XTrHa9vGbH9qUmH5F9HUkYOUX+UaFj7qVdfaR/VecB5xCwrt5ixV6y 3QIBEQ=="

  fun enccriptData(txt: String): String {
    var encoded = ""
    var encrypted: ByteArray? = null
    try {
      val publicBytes: ByteArray = Base64.decode(PUBLIC_KEY, Base64.DEFAULT)
      val keySpec = X509EncodedKeySpec(publicBytes)
      val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
      val pubKey: PublicKey = keyFactory.generatePublic(keySpec)
      val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING") //or try with "RSA"
      cipher.init(Cipher.ENCRYPT_MODE, pubKey)
      encrypted = cipher.doFinal(txt.toByteArray())
      encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return encoded
  }
}