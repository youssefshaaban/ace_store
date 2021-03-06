package com.example.mvvm_template.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener

import android.view.inputmethod.EditorInfo

import android.widget.TextView.OnEditorActionListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


object RxSearchObservable {
    fun EditText.getQueryTextChangeStateFlow():StateFlow<String>{
        val query = MutableStateFlow("")
        addTextChangedListener(onTextChanged = {
            text, start, before, count ->
            query.value=text.toString()
        })
        setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@OnEditorActionListener true
            }
            false
        })
        return query
    }
}