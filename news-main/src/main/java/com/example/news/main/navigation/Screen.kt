package com.example.news.main.navigation

import android.net.Uri

sealed class Screen(
    val route: String
) {
    /**Вложенный граф навигации: Экран новостей и экран подробной информации**/
    object Home: Screen(ROUTE_HOME)

    object NewsFeed:Screen(ROUTE_NEWS)

    object Search: Screen(ROUTE_SEARCH)

    object Favourite: Screen(ROUTE_FAVOURITE)

    companion object{
        private const val ROUTE_HOME = "home"
        private const val ROUTE_NEWS = "news"
        private const val ROUTE_FAVOURITE = "favourite"
        private const val ROUTE_SEARCH = "search"
    }

}


/***Экранирование специальных символов*/
fun String.encode(): String {
    return Uri.encode(this)
}