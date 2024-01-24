package com.composition.damoa.di.other

import android.content.Context
import com.composition.damoa.data.common.retrofit.serviceFactory.ServiceFactory
import com.composition.damoa.data.common.retrofit.serviceFactory.ServiceFactoryWithAuth
import com.composition.damoa.data.common.retrofit.serviceFactory.ServiceFactoryWithoutAuth
import com.composition.damoa.data.repository.interfaces.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @ApplicationContext context: Context,
        tokenRepository: TokenRepository,
    ): ServiceFactory = ServiceFactoryWithAuth(context, tokenRepository)

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