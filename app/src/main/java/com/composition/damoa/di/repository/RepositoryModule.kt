package com.composition.damoa.di.repository

import com.composition.damoa.data.repository.concretes.DefaultGoogleRepository
import com.composition.damoa.data.repository.concretes.DefaultUserRepository
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGoogleRepository(
        googleRepository: DefaultGoogleRepository,
    ): GoogleRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepository: DefaultUserRepository,
    ): UserRepository
}
