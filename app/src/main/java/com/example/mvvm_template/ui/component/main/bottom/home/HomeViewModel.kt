package com.example.mvvm_template.ui.component.main.bottom.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_template.core.common.DataState
import com.example.mvvm_template.domain.entity.Card
import com.example.mvvm_template.domain.entity.Category
import com.example.mvvm_template.domain.entity.HomeData
import com.example.mvvm_template.domain.entity.Slider
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
    val catogeries = MutableLiveData(arrayListOf<Category>())
    val error = SingleLiveEvent<Failure>()
    val stopLoadingMore = MutableLiveData(false)
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
                        stopLoadingMore.value = true
                    }
                    if (pageNumber == 1)
                        catogeries.value = ArrayList(it.data)
                    else {
                        catogeries.value?.addAll(it.data)
                        catogeries.value = catogeries.value
                    }
                    loadingVisibility.value = false
                } else if (it is DataState.Error) {
                    error.value = it.error
                    loadingVisibility.value = false
                }
            }
        }
    }

    fun getHome(){
        viewModelScope.launch {
            getCategories(1)
            getSlider()
        }

    }
    private fun getSlider() {
        viewModelScope.launch {
            sliderUseCase.getSlider().collect {
                sliderLiveDate.value=it
            }
        }
    }
}