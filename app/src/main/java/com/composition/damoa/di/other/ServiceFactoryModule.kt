package com.composition.damoa.di.other

import com.composition.damoa.data.common.retrofit.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceFactoryModule {
    @Singleton
    @Provides
    fun provideServiceFactory(): ServiceFactory = ServiceFactory()
}
