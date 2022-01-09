package com.example.mvvm_template

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    val xc=XC()

    @Test
    fun testtttt(){
        println(xc.x)
        x(xc)
        println(xc.x)
    }
}

fun x(xc: XC){
    xc.x=22
}

class XC {
     var x: Int = 10
}