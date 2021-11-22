package com.example.mvvm_template.core.di

import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.data.local.AppPrefrancesImp
import com.example.mvvm_template.data.repositery.*
import com.example.mvvm_template.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun binAccountRepo(accountRepoImp: AccountRepoImp):AccountRepository

    @Binds
    abstract fun binFileRepo(fileUploadRepo: FileUploadImp):FileUploadRepo

    @Binds
    abstract fun binCategoryRepo(categoryRepository: RepoCategoryImp):CategoryRepository

    @Binds
    abstract fun binProductRepo(categoryRepository: RepoProductImp):ProductRepository

    @Binds
    abstract fun binCartRepo(cartRepositoryImp: CartRepositoryImp):ICartRepository

    @Binds
    abstract fun binLookupRepo(cartRepositoryImp: LookupRepositeryImp):LookupRepository

    @Binds
    abstract fun binResourceRepo(resourcesImp: RepoResourcesImp):ResourcesRepo

    @Binds
    abstract fun bindPaymentRepo(paymentRepoImp:PaymentRepoImp):PaymentRepo
}