package com.composition.damoa.di.repository

import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.repository.concretes.DefaultConceptRepository
import com.composition.damoa.data.repository.concretes.DefaultGoogleRepository
import com.composition.damoa.data.repository.concretes.DefaultPetDetectRepository
import com.composition.damoa.data.repository.concretes.DefaultPetRepository
import com.composition.damoa.data.repository.concretes.DefaultS3ImageRepository
import com.composition.damoa.data.repository.concretes.DefaultS3ImageUrlRepository
import com.composition.damoa.data.repository.concretes.DefaultTokenRepository
import com.composition.damoa.data.repository.concretes.DefaultUserRepository
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.composition.damoa.data.repository.interfaces.PetDetectRepository
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.repository.interfaces.S3ImageRepository
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository
import com.composition.damoa.data.repository.interfaces.TokenRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.data.service.ConceptService
import com.composition.damoa.data.service.GoogleService
import com.composition.damoa.data.service.PetDetectService
import com.composition.damoa.data.service.PetService
import com.composition.damoa.data.service.S3ImageService
import com.composition.damoa.data.service.S3ImageUrlService
import com.composition.damoa.data.service.TokenService
import com.composition.damoa.data.service.UserService
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
    ): GoogleRepository = DefaultGoogleRepository(googleService)

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
        tokenRepository: TokenRepository,
    ): UserRepository = DefaultUserRepository(userService, tokenRepository)

    @Singleton
    @Provides
    fun provideTokenRepository(
        tokenService: TokenService,
        tokenDataSource: TokenDataSource,
    ): TokenRepository = DefaultTokenRepository(tokenService, tokenDataSource)

    @Singleton
    @Provides
    fun provideConceptRepository(
        conceptService: ConceptService,
    ): ConceptRepository = DefaultConceptRepository(conceptService)

    @Singleton
    @Provides
    fun providePetRepository(
        petService: PetService,
    ): PetRepository = DefaultPetRepository(petService)

    @Singleton
    @Provides
    fun provideS3ImageUrlRepository(
        s3ImageUrlService: S3ImageUrlService,
    ): S3ImageUrlRepository = DefaultS3ImageUrlRepository(s3ImageUrlService)

    @Singleton
    @Provides
    fun providePetDetectRepository(
        petDetectService: PetDetectService,
    ): PetDetectRepository = DefaultPetDetectRepository(petDetectService)

    @Singleton
    @Provides
    fun provideS3ImageRepository(
        s3ImageService: S3ImageService,
    ): S3ImageRepository = DefaultS3ImageRepository(s3ImageService)
}
