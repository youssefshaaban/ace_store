package com.example.mvvm_template.domain.interactor.resources

import com.example.mvvm_template.domain.repository.ResourcesRepo
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UseCaseFactory @Inject constructor(private val resourcesRepo: ResourcesRepo,val ioDispatcher: CoroutineContext) {

    fun createUseCaseResource(value:Int):IGetResourceUseCase{
        when(value){
            ResourceUseCaseType.ABOUT.value-> return GetAboutUseCase(resourcesRepo,ioDispatcher)
            ResourceUseCaseType.REFUND_POLICY.value->return GetAboutUseCase(resourcesRepo,ioDispatcher)
            else->Throw
        }
    }
}

enum class ResourceUseCaseType(val value:Int){
    ABOUT(1),REFUND_POLICY(2)
}