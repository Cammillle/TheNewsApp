package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class SortBy {
    @SerializedName("relevancy")
    RELEVANCY,

    @SerializedName("popularity")
    POPULARITY,

    @SerializedName("publishedAt")
    PUBLISHED_AT

}