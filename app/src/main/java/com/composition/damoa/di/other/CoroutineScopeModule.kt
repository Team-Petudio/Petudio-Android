package com.composition.damoa.di.other

import com.composition.damoa.PetudioApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineScopeModule {

    @Provides
    @Singleton
    @ApplicationIoDispatcherScope
    fun provideApplicationIoDispatcherScope() = PetudioApplication.scope
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationIoDispatcherScope