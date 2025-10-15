package com.example.news.data.models

import java.util.Date

data class Article(
    val id: Long = ID_NONE,
    val source: Source?,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String?
) {
    companion object {
        const val ID_NONE: Long = 0
    }
}

data class Source(
    val id: String?,
    val name: String?
)