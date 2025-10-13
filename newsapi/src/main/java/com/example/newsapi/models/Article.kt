package com.example.newsapi.models

import com.example.newsapi.utils.DateSerializer
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.Date

//"source": {
//"id": null,
//"name": "Gizmodo.com"
//},
//"author": "Lucas Ropek",
//"title": "Under Trump, ‘Bitcoin Jesus’ May Be Redeemed",
//"description": "A new report claims that Roger Ver may have reached a deal with the government.",
//"url": "https://gizmodo.com/under-trump-bitcoin-jesus-may-be-redeemed-2000670784",
//"urlToImage": "https://gizmodo.com/app/uploads/2025/10/Roger-Ver-1200x675.jpg",
//"publishedAt": "2025-10-10T13:50:09Z",
//"content": "Ever since the crypto industry helped bankroll his presidential campaign last year, Trump and the HODLers have been on pretty good terms. The Trump family is busy launching its own crypto ventures wh… [+2568 chars]"
//},

@Serializable
data class Article(
    @SerializedName("source") val source: Source,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") @Serializable(with = DateSerializer::class) val publishedAt: Date,
    @SerializedName("content") val content: String
)
