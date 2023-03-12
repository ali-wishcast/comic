package com.android.comic.data.comic.remote

import com.android.comic.data.base.BaseApiFlow
import com.android.comic.data.comic.entities.Comic
import com.android.comic.data.comic.remote.ComicApi
import javax.inject.Inject

class ComicRemoteDataSource @Inject constructor(
    private val comicApi: ComicApi,
) : BaseApiFlow() {
    suspend fun getComicDetail(id: Int): Result<Comic> =
        handleApi {
            comicApi.getComicDetail(id)
        }
}