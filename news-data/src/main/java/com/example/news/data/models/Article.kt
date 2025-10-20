package com.example.news.data.models

import android.os.Parcelable
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Long = ID_NONE,
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: Date?,
    val content: String?
) : Parcelable {
    companion object {
        const val ID_NONE: Long = 0
    }
}

@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable