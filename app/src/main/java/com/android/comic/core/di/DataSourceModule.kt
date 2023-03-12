package com.android.comic.core.di

import com.android.comic.data.comic.ComicApi
import com.android.comic.data.comic.ComicDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal class DataSourceModule {

    @Provides
    fun provideComicDataSource(
        comicApi: ComicApi,
    ): ComicDataSource {
        return ComicDataSource(comicApi)
    }
}