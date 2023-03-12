package com.android.comic.core.di

import com.android.comic.data.comic.ComicDataSource
import com.android.comic.data.comic.ComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal class RepositoryModule {

    @Provides
    fun provideComicRepository(comicDataSource: ComicDataSource): ComicRepository {
        return ComicRepository(comicDataSource)
    }
}