package com.example.mvvm_template.ui.component.main.bottom.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.HomeData
import com.example.mvvm_template.domain.error.Failure
import com.example.mvvm_template.domain.interactor.GetSliderUseCase
import com.example.mvvm_template.domain.interactor.category.GetCartegoryPaginationUseCase
import com.example.mvvm_template.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCartegoryPaginationUseCase: GetCartegoryPaginationUseCase,
    private val sliderUseCase: GetSliderUseCase
) :
    ViewModel() {
    val loadingVisibility = MutableLiveData(false)
    private val catecories = arrayListOf<Category>()
    val catogeriesMuliveDate= MutableLiveData<List<Category>>(emptyList())
    val error = SingleLiveEvent<Failure>()
    var isLastPage: Boolean = false
    val sliderLiveDate = MutableLiveData<DataState<HomeData>>()
    fun getCategories(pageNumber: Int) {
        viewModelScope.launch {
            //categoryDataLiveDate.value=DataState.Loading
            loadingVisibility.value = true
            getCartegoryPaginationUseCase.execute(
                GetCartegoryPaginationUseCase.ParamPageCategory(
                    pagNumber = pageNumber,
                    subCategories = true
                )
            ).collect {
                if (it is DataState.Success) {
                    if (it.data.size < 20) {
                        isLastPage = true
                    }
                    catecories.addAll(it.data)
                    catogeriesMuliveDate.value=it.data
                    loadingVisibility.value = false
                } else if (it is DataState.Error) {
                    error.value = it.error
                    loadingVisibility.value = false
                }
            }
        }
    }

    fun getHome() {
        viewModelScope.launch {
            getCategories(1)
            getSlider()
        }

    }

    fun isLoading():Boolean= (loadingVisibility.value==null || loadingVisibility.value!!)
    private fun getSlider() {
        viewModelScope.launch {
            sliderUseCase.getSlider().collect {
                sliderLiveDate.value = it
            }
        }
    }
}