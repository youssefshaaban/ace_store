package com.example.mvvm_template.utils

import java.io.File

fun File.toMegaBytes():Int{
    return (this.length()/(1024*1024)).toInt()
}

fun File.toKiloBytes():Int{
    return (this.length()/(1024*1024)).toInt()
}


fun File.toGigaBytes():Int{
    return (this.length()/(1024*1024*1024)).toInt()
}

fun getPhoneWithoutZero(mobil: String): String {
    var phone = mobil
    if (phone.startsWith("0")) {
        phone = phone.removePrefix("0")
    }
    return phone
}