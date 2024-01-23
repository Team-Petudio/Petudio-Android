package com.composition.damoa.di.service

import com.composition.damoa.data.common.retrofit.ServiceFactory
import com.composition.damoa.data.service.GoogleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    fun provideGoogleService(
        serviceFactory: ServiceFactory,
    ): GoogleService = serviceFactory.create(GoogleService::class.java)
}
