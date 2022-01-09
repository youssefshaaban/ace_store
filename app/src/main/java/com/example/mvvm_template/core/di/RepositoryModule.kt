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
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun binAccountRepo(accountRepoImp: AccountRepoImp):AccountRepository

    @Binds
    @Singleton
    abstract fun binFileRepo(fileUploadRepo: FileUploadImp):FileUploadRepo

    @Binds
    @Singleton
    abstract fun binCategoryRepo(categoryRepository: RepoCategoryImp):CategoryRepository

    @Binds
    @Singleton
    abstract fun binProductRepo(categoryRepository: RepoProductImp):ProductRepository

    @Binds
    @Singleton
    abstract fun binCartRepo(cartRepositoryImp: CartRepositoryImp):ICartRepository

    @Binds
    @Singleton
    abstract fun binLookupRepo(cartRepositoryImp: LookupRepositeryImp):LookupRepository

    @Binds
    @Singleton
    abstract fun binResourceRepo(resourcesImp: RepoResourcesImp):ResourcesRepo

    @Binds
    @Singleton
    abstract fun binOrderRepo(resourcesImp: RepoOrderImp):OrderRepository

    @Binds
    @Singleton
    abstract fun binCustomerRepo(resourcesImp: CustomerRepositoryImp):CustomerRepo
}