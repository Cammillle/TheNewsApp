package com.example.newsapi.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

//"source": {
//"id": null,
//"name": "Gizmodo.com"
//},

@Serializable
data class Source(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
)