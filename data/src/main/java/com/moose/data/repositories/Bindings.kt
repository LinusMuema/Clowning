package com.moose.data.repositories

import com.moose.domain.repositories.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Bindings {

    @Binds
    abstract fun bindAuthRepository(impl: GithubRepositoryImpl): GithubRepository

}