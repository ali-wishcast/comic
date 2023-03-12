package com.android.comic.data.comic

import com.android.comic.data.comic.entities.Comic
import retrofit2.Response
import retrofit2.http.*

interface ComicApi {

    @GET("/{id}/info.0.json")
    suspend fun getComicDetail(@Path("id") id: Int): Response<Comic>

}