package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


//"status": "ok",
//"totalResults": 8186,
//-"articles": []

@Serializable
data class Response<E>(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<E>
    )