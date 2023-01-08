package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun bindsPostingRepository(postingRepositoryImpl: PostingRepositoryImpl): PostingRepository

    @Singleton
    @Binds
    abstract fun bindsProfileRepositroy(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun bindsSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository


}