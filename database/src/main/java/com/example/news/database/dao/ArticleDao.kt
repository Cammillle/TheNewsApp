package com.example.news.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.news.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Query("select * from articles")
    fun getAll(): Flow<List<ArticleDBO>>

    @Insert
    suspend fun insert(article: ArticleDBO)

    @Delete
    suspend fun remove(article: ArticleDBO)

    @Query("delete from articles")
    suspend fun clean()
}