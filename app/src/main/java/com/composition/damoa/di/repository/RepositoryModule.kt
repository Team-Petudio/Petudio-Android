package com.composition.damoa.di.repository

import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.network.service.ConceptService
import com.composition.damoa.data.network.service.GiftCardService
import com.composition.damoa.data.network.service.GoogleService
import com.composition.damoa.data.network.service.PetDetectService
import com.composition.damoa.data.network.service.PetFeedService
import com.composition.damoa.data.network.service.PetService
import com.composition.damoa.data.network.service.S3ImageService
import com.composition.damoa.data.network.service.S3ImageUrlService
import com.composition.damoa.data.network.service.TokenService
import com.composition.damoa.data.network.service.UserService
import com.composition.damoa.data.repository.concretes.DefaultConceptRepository
import com.composition.damoa.data.repository.concretes.DefaultGiftCardRepository
import com.composition.damoa.data.repository.concretes.DefaultGoogleRepository
import com.composition.damoa.data.repository.concretes.DefaultPetDetectRepository
import com.composition.damoa.data.repository.concretes.DefaultPetFeedRepository
import com.composition.damoa.data.repository.concretes.DefaultPetRepository
import com.composition.damoa.data.repository.concretes.DefaultS3ImageRepository
import com.composition.damoa.data.repository.concretes.DefaultS3ImageUrlRepository
import com.composition.damoa.data.repository.concretes.DefaultTokenRepository
import com.composition.damoa.data.repository.concretes.DefaultUserRepository
import com.composition.damoa.data.repository.concretes.FakeAlbumRepository
import com.composition.damoa.data.repository.interfaces.AlbumRepository
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.GiftCardRepository
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.composition.damoa.data.repository.interfaces.PetDetectRepository
import com.composition.damoa.data.repository.interfaces.PetFeedRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.repository.interfaces.S3ImageRepository
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository
import com.composition.damoa.data.repository.interfaces.TokenRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGoogleRepository(
        googleService: GoogleService,
    ): GoogleRepository = DefaultGoogleRepository(service = googleService)

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
        tokenRepository: TokenRepository,
    ): UserRepository = DefaultUserRepository(
        service = userService,
        tokenRepository = tokenRepository
    )

    @Singleton
    @Provides
    fun provideTokenRepository(
        tokenService: TokenService,
        tokenDataSource: TokenDataSource,
    ): TokenRepository = DefaultTokenRepository(
        service = tokenService,
        tokenDataSource = tokenDataSource
    )

    @Singleton
    @Provides
    fun provideConceptRepository(
        conceptService: ConceptService,
    ): ConceptRepository = DefaultConceptRepository(service = conceptService)

    @Singleton
    @Provides
    fun providePetRepository(
        petService: PetService,
    ): PetRepository = DefaultPetRepository(service = petService)

    @Singleton
    @Provides
    fun provideS3ImageUrlRepository(
        s3ImageUrlService: S3ImageUrlService,
    ): S3ImageUrlRepository = DefaultS3ImageUrlRepository(service = s3ImageUrlService)

    @Singleton
    @Provides
    fun providePetDetectRepository(
        petDetectService: PetDetectService,
    ): PetDetectRepository = DefaultPetDetectRepository(service = petDetectService)

    @Singleton
    @Provides
    fun provideS3ImageRepository(
        s3ImageService: S3ImageService,
    ): S3ImageRepository = DefaultS3ImageRepository(service = s3ImageService)

    @Singleton
    @Provides
    fun provideGiftCardRepository(
        giftCardService: GiftCardService,
    ): GiftCardRepository = DefaultGiftCardRepository(service = giftCardService)

    @Singleton
    @Provides
    fun providePetFeedRepository(
        petFeedService: PetFeedService,
    ): PetFeedRepository = DefaultPetFeedRepository(service = petFeedService)

    @Singleton
    @Provides
    fun provideAlbumRepository(
        // TODO : AlbumService 종속 항목 추가
    ): AlbumRepository = FakeAlbumRepository()
}
