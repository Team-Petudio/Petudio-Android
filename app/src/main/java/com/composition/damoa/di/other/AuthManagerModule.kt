package com.composition.damoa.di.other

import android.content.Context
import com.composition.damoa.data.repository.concretes.DefaultGoogleRepository
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
    @GoogleAuth
    @Provides
    @ActivityScoped
    fun provideGoogleAuthManager(
        @ActivityContext context: Context,
        googleRepository: DefaultGoogleRepository,
    ): AuthManager = GoogleAuthManager(context, googleRepository)

    @KakaoAuth
    @Provides
    @ActivityScoped
    fun provideKakaoAuthManager(
        @ApplicationContext context: Context,
    ): AuthManager = KakaoAuthManager(context)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GoogleAuth

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class KakaoAuth