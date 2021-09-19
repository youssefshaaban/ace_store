package com.example.mvvm_template.core.di

import com.example.mvvm_template.data.repositery.AccountRepoImp
import com.example.mvvm_template.data.repositery.FileUploadImp
import com.example.mvvm_template.domain.repository.AccountRepository
import com.example.mvvm_template.domain.repository.FileUploadRepo
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
    abstract fun binAccountRepo(fileUploadRepo: FileUploadRepo):FileUploadImp
}