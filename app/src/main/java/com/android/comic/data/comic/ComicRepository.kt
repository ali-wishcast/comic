package com.android.comic.data.comic

import com.android.comic.data.comic.entities.Comic
import com.android.comic.data.comic.remote.ComicRemoteDataSource
import javax.inject.Inject

interface ComicRepository {
    suspend fun getComicDetail(id: Int): Result<Comic>
}

class DefaultComicRepository @Inject constructor(
    private val comicRemoteDataSource: ComicRemoteDataSource,
) : ComicRepository {

    override suspend fun getComicDetail(id: Int): Result<Comic> {
        return comicRemoteDataSource.getComicDetail(id)
    }
}