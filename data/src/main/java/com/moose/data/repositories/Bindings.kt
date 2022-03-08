package com.moose.data.repositories

import com.moose.domain.repositories.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Bindings {

    @Binds
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository

}