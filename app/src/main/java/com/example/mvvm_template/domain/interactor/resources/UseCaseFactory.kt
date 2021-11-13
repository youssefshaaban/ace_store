package com.example.mvvm_template.domain.interactor.resources

import com.example.mvvm_template.domain.repository.ResourcesRepo
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UseCaseFactory @Inject constructor(
    private val resourcesRepo: ResourcesRepo,
    val ioDispatcher: CoroutineContext
) {
    @kotlin.jvm.Throws(NothingTypeException::class)
    fun createUseCaseResource(value: Int): IGetResourceUseCase {
        return when (value) {
            ResourceUseCaseType.ABOUT.value -> GetAboutUseCase(resourcesRepo, ioDispatcher)
            ResourceUseCaseType.REFUND_POLICY.value -> GetAboutUseCase(
                resourcesRepo,
                ioDispatcher
            )
            else -> throw NothingTypeException()
        }
    }
}

class NothingTypeException : Throwable()


enum class ResourceUseCaseType(val value: Int) {
    ABOUT(1), REFUND_POLICY(2)
}