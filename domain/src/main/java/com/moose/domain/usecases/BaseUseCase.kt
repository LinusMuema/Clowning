package com.moose.domain.usecases

interface BaseUseCase<T> {
    suspend fun invoke(): T
}