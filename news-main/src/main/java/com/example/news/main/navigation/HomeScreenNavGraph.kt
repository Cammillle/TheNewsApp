package com.example.news.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.news.main.ArticleUI

/**Вложенный граф навигации*/

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    articlePostContent: @Composable (ArticleUI) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            Screen.ArticlePost.route,
            arguments = listOf(

                navArgument(Screen.ARTICLE_KEY) {
                    type = ArticleUI.NavigationType
                }
            )
        ) {
            val article = it.arguments?.getParcelable<ArticleUI>(Screen.ARTICLE_KEY)
                ?: throw RuntimeException("Args is null")
            articlePostContent(article)
        }
    }
}