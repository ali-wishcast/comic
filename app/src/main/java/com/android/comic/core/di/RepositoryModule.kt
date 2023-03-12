package com.android.comic.core.di

import com.android.comic.data.comic.remote.ComicRemoteDataSource
import com.android.comic.data.comic.ComicRepository
import com.android.comic.data.comic.DefaultComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal class RepositoryModule {

    @Provides
    fun provideComicRepository(comicRemoteDataSource: ComicRemoteDataSource): ComicRepository {
        return DefaultComicRepository(comicRemoteDataSource)
    }
}