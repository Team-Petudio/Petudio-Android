package com.composition.damoa.di.other

import com.composition.damoa.data.network.retrofit.serviceFactory.ServiceFactory
import com.composition.damoa.data.network.retrofit.serviceFactory.ServiceFactoryWithAuth
import com.composition.damoa.data.network.retrofit.serviceFactory.ServiceFactoryWithoutAuth
import com.composition.damoa.data.repository.interfaces.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ServiceFactoryModule {

    @Singleton
    @ServiceFactoryWithAuthQualifier
    @Provides
    fun provideServiceFactoryWithAuth(
        tokenRepository: TokenRepository,
    ): ServiceFactory = ServiceFactoryWithAuth(tokenRepository)

    @Singleton
    @ServiceFactoryWithoutAuthQualifier
    @Provides
    fun provideServiceFactoryWithoutAuth(): ServiceFactory = ServiceFactoryWithoutAuth()
}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ServiceFactoryWithAuthQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ServiceFactoryWithoutAuthQualifier