package com.example.news.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news.main.ArticleUI

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    articleListContent: @Composable () -> Unit,
    articlePostContent: @Composable (ArticleUI) -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph(
            articleListContent,
            articlePostContent
        )

        composable(route = Screen.Search.route) {
            searchScreenContent()
        }
        composable(route = Screen.Favourite.route) {
            favouriteScreenContent()
        }
    }
}