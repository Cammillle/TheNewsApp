package com.example.news.main

import com.example.news.data.models.Source
import java.util.Date


data class ArticleUI(
    val id: Long,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: Date?,
    val url: String?,
    val author: String?,
    val source: Source?,
    val content: String?
)
