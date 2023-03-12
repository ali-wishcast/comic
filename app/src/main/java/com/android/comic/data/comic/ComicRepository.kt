package com.android.comic.data.comic

import com.android.comic.data.comic.entities.Comic
import javax.inject.Inject

interface ComicRepository {
    suspend fun getComicDetail(id: Int): Result<Comic>
}

class DefaultComicRepository @Inject constructor(
    private val comicDataSource: ComicDataSource,
) : ComicRepository {

    override suspend fun getComicDetail(id: Int): Result<Comic> {
        return comicDataSource.getComicDetail(id)
    }
}