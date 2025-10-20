package com.example.news.main

import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.example.news.data.models.Source
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ArticleUI(
    val id: Long,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: Date?,
    val url: String?,
    val author: String?,
    val source: Source?,
    val content: String?
) : Parcelable {
    companion object {

        val NavigationType: NavType<ArticleUI> = object : NavType<ArticleUI>(false) {
            override fun put(
                bundle: SavedState,
                key: String,
                value: ArticleUI
            ) {
                bundle.putParcelable(key, value)
            }

            override fun get(
                bundle: SavedState,
                key: String
            ): ArticleUI? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): ArticleUI {
                return Gson().fromJson(value, ArticleUI::class.java)
            }

        }
    }
}
