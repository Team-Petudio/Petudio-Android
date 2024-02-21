package com.composition.damoa.di.service

import com.composition.damoa.data.common.retrofit.serviceFactory.ServiceFactory
import com.composition.damoa.data.service.ConceptService
import com.composition.damoa.data.service.GiftCardService
import com.composition.damoa.data.service.GoogleService
import com.composition.damoa.data.service.PetDetectService
import com.composition.damoa.data.service.PetFeedService
import com.composition.damoa.data.service.PetService
import com.composition.damoa.data.service.S3ImageService
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
    ): UserService = serviceFactory.create(service = UserService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(
        @ServiceFactoryWithoutAuthQualifier serviceFactory: ServiceFactory,
    ): TokenService = serviceFactory.create(service = TokenService::class.java)

    @Provides
    @Singleton
    fun provideConceptService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): ConceptService = serviceFactory.create(service = ConceptService::class.java)

    @Provides
    @Singleton
    fun providePetService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): PetService = serviceFactory.create(service = PetService::class.java)

    @Provides
    @Singleton
    fun provideS3ImageUrlService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): S3ImageUrlService = serviceFactory.create(service = S3ImageUrlService::class.java)

    @Provides
    @Singleton
    fun providePetDetectService(
        @ServiceFactoryWithoutAuthQualifier serviceFactory: ServiceFactory,
    ): PetDetectService = serviceFactory.create(
        service = PetDetectService::class.java,
        baseUrl = PetDetectService.BASE_URL,
    )

    @Provides
    @Singleton
    fun provideS3ImageService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): S3ImageService = serviceFactory.create(
        service = S3ImageService::class.java,
        baseUrl = S3ImageService.BASE_URL,
    )

    @Provides
    @Singleton
    fun provideGiftCardService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): GiftCardService = serviceFactory.create(service = GiftCardService::class.java)

    @Provides
    @Singleton
    fun providePetFeedService(
        @ServiceFactoryWithAuthQualifier serviceFactory: ServiceFactory,
    ): PetFeedService = serviceFactory.create(service = PetFeedService::class.java)
}
