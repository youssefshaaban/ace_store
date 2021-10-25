package com.example.mvvm_template.domain.dto

enum class FieldsFormValidation (val value: Int){
    EMPTY_EMAIL(1),
    INVALID_EMAIL(2),
    EMPTY_PASSWORD(3),
    EMPTY_FIRSTNAME(4),
    EMPTY_LASTNAME(5),
}