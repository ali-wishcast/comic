package com.android.comic.data.comic

import com.android.comic.data.comic.entities.Comic
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private val comicDataSource: ComicDataSource,
) {

    suspend fun getComicDetail(id: Int): Result<Comic> {
        return comicDataSource.getComicDetail(id)
    }
}