package com.android.comic.data.comic

import com.android.comic.data.base.BaseApiFlow
import com.android.comic.data.comic.entities.Comic
import javax.inject.Inject

class ComicDataSource @Inject constructor(
    private val comicApi: ComicApi,
) : BaseApiFlow() {
    suspend fun getComicDetail(id: Int): Result<Comic> =
        handleApi {
            comicApi.getComicDetail(id)
        }
}