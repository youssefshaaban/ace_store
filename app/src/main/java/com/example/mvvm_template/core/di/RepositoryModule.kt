package com.example.mvvm_template.core.di

import com.example.mvvm_template.data.local.AppPrefrances
import com.example.mvvm_template.data.local.AppPrefrancesImp
import com.example.mvvm_template.data.repositery.AccountRepoImp
import com.example.mvvm_template.data.repositery.FileUploadImp
import com.example.mvvm_template.data.repositery.RepoCategoryImp
import com.example.mvvm_template.data.repositery.RepoProductImp
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.domain.repository.CategoryRepository
import com.example.mvvm_template.domain.repository.FileUploadRepo
import com.example.mvvm_template.domain.repository.ProductRepository
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


}