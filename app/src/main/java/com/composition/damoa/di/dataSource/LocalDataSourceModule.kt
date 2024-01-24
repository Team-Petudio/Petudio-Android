package com.composition.damoa.di.dataSource

import android.content.Context
import android.content.SharedPreferences
import com.composition.damoa.data.dataSource.local.concretes.DefaultTokenDataSource
import com.composition.damoa.data.dataSource.local.concretes.DefaultTokenDataSource.Companion.PETUDIO_PREF_KEY
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(PETUDIO_PREF_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDefaultTokenDataSource(
        preferences: SharedPreferences,
    ): TokenDataSource = DefaultTokenDataSource(preferences)
}