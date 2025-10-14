package com.example.news.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.news.database.utils.Converters
import java.util.Date

@Entity(tableName = "articles")
@TypeConverters(Converters::class)
data class ArticleDBO(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Embedded(prefix = "source.") val source: Source,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("urlToImage") val urlToImage: String,
    @ColumnInfo("publishedAt") val publishedAt: Date,
    @ColumnInfo("content") val content: String
)

data class Source(
    @ColumnInfo("id") val id: String? = null,
    @ColumnInfo("name") val name: String? = null
)
