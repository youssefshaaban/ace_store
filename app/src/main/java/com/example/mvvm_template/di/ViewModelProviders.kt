package com.example.mvvm_template.di

import com.example.mvvm_template.ui.component.card_categories.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { CardViewModel() }
}