package com.example.mvvm_template.data.mapper

interface BaseMapper<T,R> {
    fun map(t:T):R
}