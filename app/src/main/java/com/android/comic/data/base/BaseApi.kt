package com.android.comic.data.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

open class BaseApiFlow {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>,
    ): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Throwable(message = "${response.code()} *** ${response.message()}"))
                }
            } catch (e: HttpException) {
                Result.failure(e.cause!!)
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
}

