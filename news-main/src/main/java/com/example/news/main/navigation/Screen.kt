package com.example.news.main.navigation

import android.net.Uri
import com.example.news.data.models.Article
import com.example.news.main.ArticleUI
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {
    /**Вложенный граф навигации: Экран новостей и экран подробной информации**/
    object Home : Screen(ROUTE_HOME)

    object ArticlePost : Screen("$ROUTE_ARTICLE_POST/{article}") {

        fun getRouteWithArgs(article: ArticleUI): String {
            val articleJson = Gson().toJson(article).encode()
            return "$ROUTE_ARTICLE_POST/$articleJson"
        }
    }

    object NewsFeed : Screen(ROUTE_NEWS)

    object Search : Screen(ROUTE_SEARCH)

    object Favourite : Screen(ROUTE_FAVOURITE)

    companion object {
        const val ARTICLE_KEY = "article"
        private const val ROUTE_HOME = "home"

        private const val ROUTE_ARTICLE_POST = "article_post"
        private const val ROUTE_NEWS = "news"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_SEARCH = "search"
    }

}


/***Экранирование специальных символов*/
fun String.encode(): String {
    return Uri.encode(this)
}