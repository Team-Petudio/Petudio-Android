package com.composition.damoa.di.dataSource

import android.content.Context
import com.composition.damoa.data.dataSource.local.concretes.DataStoreTokenDataSource
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.dataSource.local.serializer.TokenSerializer
import com.composition.damoa.di.other.ApplicationIoDispatcherScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideDataStoreTokenDataSource(
        @ApplicationIoDispatcherScope scope: CoroutineScope,
        @ApplicationContext context: Context,
    ): TokenDataSource = DataStoreTokenDataSource(
        scope = scope,
        dataStore = TokenSerializer.getDataStore(context)
    )
}
