package com.example.newsapi.models

import com.example.newsapi.utils.DateSerializer
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ArticleDTO(
    @SerializedName("source") val source: SourceDTO?,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt")
    @Serializable(with = DateSerializer::class)
    val publishedAt: Date,
    @SerializedName("content") val content: String?
)
