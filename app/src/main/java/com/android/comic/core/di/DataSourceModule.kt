package com.android.comic.core.di

import com.android.comic.data.comic.remote.ComicApi
import com.android.comic.data.comic.remote.ComicRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal class DataSourceModule {

    @Provides
    fun provideComicRemoteDataSource(
        comicApi: ComicApi,
    ): ComicRemoteDataSource {
        return ComicRemoteDataSource(comicApi)
    }
}