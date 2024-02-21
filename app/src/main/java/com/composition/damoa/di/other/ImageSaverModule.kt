package com.composition.damoa.di.other

import android.content.Context
import com.composition.damoa.presentation.common.utils.imageSaver.ImageSaver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ImageSaverModule {

    @Provides
    @Singleton
    fun provideImageSaver(
        @ApplicationContext context: Context,
    ): ImageSaver = ImageSaver(context)
}