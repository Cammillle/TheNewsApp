package com.example.news.main

import com.example.news.data.models.Source


public data class ArticleUI(
    val id: Long,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val url: String?,
    val author:String?,
    val source: Source?
)
