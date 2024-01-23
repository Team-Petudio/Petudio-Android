package com.composition.damoa.di.service

import com.composition.damoa.data.common.retrofit.ServiceFactory
import com.composition.damoa.data.service.GoogleService
import com.composition.damoa.data.service.UserService
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

    @Provides
    fun provideUserService(
        serviceFactory: ServiceFactory,
    ): UserService = serviceFactory.create(UserService::class.java)
}
