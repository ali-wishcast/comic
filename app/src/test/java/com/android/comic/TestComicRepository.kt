package com.android.comic

import com.android.comic.data.comic.ComicRepository
import com.android.comic.data.comic.entities.Comic

class TestComicRepository : ComicRepository {

    private var shouldThrowError = false

    val comicList: MutableList<Comic> = mutableListOf()

    override suspend fun getComicDetail(id: Int): Result<Comic> {
        if (shouldThrowError) {
            throw Exception("Test exception")
        }
        return Result.success(comicList.first { id == it.num })
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }
}