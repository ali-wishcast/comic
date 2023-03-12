package com.android.comic.data.comic.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    val num: Int,
    val safe_title: String,
    val title: String,
    val transcript: String,
    val year: String
) {
    fun getPublishDate(): String {
        return "$day/$month/$year"
    }
}