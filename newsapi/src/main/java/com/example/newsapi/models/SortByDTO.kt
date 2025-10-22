package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class SortByDTO {
    @SerializedName("relevancy")
    relevancy,

    @SerializedName("popularity")
    popularity,

    @SerializedName("publishedAt")
    publishedAt

}