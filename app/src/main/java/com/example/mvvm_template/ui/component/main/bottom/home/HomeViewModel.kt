package com.example.mvvm_template.ui.component.main.bottom.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.interactor.category.GetCartegoryPaginationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCartegoryPaginationUseCase: GetCartegoryPaginationUseCase): ViewModel() {

    private val categoryDataLiveDate = MutableLiveData<DataState<List<Category>>>()
    val observeCategoryLiveDate: LiveData<DataState<List<Category>>> get() = categoryDataLiveDate

    fun getCategories(pageNumber:Int){
        viewModelScope.launch {
            categoryDataLiveDate.value=DataState.Loading
            getCartegoryPaginationUseCase.execute(GetCartegoryPaginationUseCase.ParamPageCategory(pagNumber = pageNumber,subCategories = true)).collect {
                categoryDataLiveDate.value=it
            }
        }
    }
}