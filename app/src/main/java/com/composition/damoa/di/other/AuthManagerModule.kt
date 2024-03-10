package com.composition.damoa.di.other

import android.content.Context
import com.composition.damoa.data.model.User.SocialType.*
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.composition.damoa.presentation.screens.login.authManager.AuthCompat
import com.composition.damoa.presentation.screens.login.authManager.AuthManager
import com.composition.damoa.presentation.screens.login.authManager.GoogleAuthManager
import com.composition.damoa.presentation.screens.login.authManager.KakaoAuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Qualifier


@Module
@InstallIn(ActivityComponent::class)
class AuthManagerModule {

    @AuthCompatQualifier
    @ActivityScoped
    @Provides
    fun provideAuthCompat(
        @GoogleAuthQualifier googleAuthManager: AuthManager,
        @KakaoAuthQualifier kakaoAuthManager: AuthManager,
    ): AuthManager = AuthCompat(
        GOOGLE to googleAuthManager,
        KAKAO to kakaoAuthManager
    )

    @GoogleAuthQualifier
    @ActivityScoped
    @Provides
    fun provideGoogleAuthManager(
        @ActivityContext context: Context,
        googleRepository: GoogleRepository,
    ): AuthManager = GoogleAuthManager(context, googleRepository)

    @KakaoAuthQualifier
    @ActivityScoped
    @Provides
    fun provideKakaoAuthManager(
        @ApplicationContext context: Context,
    ): AuthManager = KakaoAuthManager(context)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuthCompatQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GoogleAuthQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class KakaoAuthQualifier