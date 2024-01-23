package com.composition.damoa.di.repository

import com.composition.damoa.data.repository.GoogleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(
        impl: GoogleRepository,
    ): GoogleRepository
}
