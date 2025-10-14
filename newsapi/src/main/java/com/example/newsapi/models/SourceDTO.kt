package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SourceDTO(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
)