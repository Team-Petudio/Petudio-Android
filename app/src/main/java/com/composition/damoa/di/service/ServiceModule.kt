package com.composition.damoa.di.service

import com.composition.damoa.data.common.retrofit.serviceFactory.ServiceFactory
import com.composition.damoa.data.service.ConceptService
import com.composition.damoa.data.service.GoogleService
import com.composition.damoa.data.service.PetService
import com.composition.damoa.data.service.S3ImageUrlService
import com.composition.damoa.data.service.TokenService
import com.composition.damoa.data.service.UserService
import com.composition.damoa.di.other.ServiceFactoryWithAuthQualifier
import com.composition.damoa.di.other.ServiceFactoryWithoutAuthQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideGoogleService(
        @ServiceFactoryWithoutAuthQualifier serviceFactory: ServiceFactory,
    ): GoogleService = serviceFactory.create(
        service = GoogleService::class.java,
        baseUrl = GoogleService.BASE_URL,
    )

    @Provides
    @Singleton
    fun provideUserService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): UserService = serviceFactory.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(
        @ServiceFactoryWithoutAuthQualifier serviceFactory: ServiceFactory,
    ): TokenService = serviceFactory.create(TokenService::class.java)

    @Provides
    @Singleton
    fun provideConceptService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): ConceptService = serviceFactory.create(ConceptService::class.java)

    @Provides
    @Singleton
    fun providePetService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): PetService = serviceFactory.create(PetService::class.java)

    @Provides
    @Singleton
    fun provideS3ImageService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): S3ImageUrlService = serviceFactory.create(S3ImageUrlService::class.java)
}
