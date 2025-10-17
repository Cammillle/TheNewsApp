package com.example.news.main.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news.main.NewsMainViewModel

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    articleListContent: @Composable () -> Unit,
    //articlePostContent: @Composable (FeedPost) -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            articleListContent()
        }
        composable(route = Screen.Search.route) {
            searchScreenContent()
        }
        composable(route = Screen.Favourite.route) {
            favouriteScreenContent()
        }
    }
}