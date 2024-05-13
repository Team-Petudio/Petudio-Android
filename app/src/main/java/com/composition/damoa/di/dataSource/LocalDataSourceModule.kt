package com.composition.damoa.di.dataSource

import CryptoManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.composition.damoa.data.dataSource.local.concretes.DataStoreTokenDataSource
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.dataSource.local.serializer.TokenSerializer
import com.composition.damoa.data.model.User.Token
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
    fun provideDataStoreTokenDataSource(
        @ApplicationContext context: Context,
    ): TokenDataSource = DataStoreTokenDataSource(
        dataStore = context.tokenDataStore,
    )
}


private const val DATA_STORE_FILE_NAME = "token_data_store.json"

private val Context.tokenDataStore: DataStore<Token> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = TokenSerializer(CryptoManager())
)
