package com.android.comic.core.di

import com.android.comic.data.comic.ComicApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun provideComicApi(retrofit: Retrofit): ComicApi {
        return retrofit.create(ComicApi::class.java)
    }
}