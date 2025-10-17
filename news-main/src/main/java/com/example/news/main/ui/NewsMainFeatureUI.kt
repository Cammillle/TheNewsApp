package com.example.news.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.news.main.AppTextStyles
import com.example.news.main.NewsMainViewModel
import com.example.news.main.R
import com.example.news.main.State
import com.example.news.main.navigation.AppNavGraph
import com.example.news.main.navigation.rememberNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsMainScreen(
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles,
    viewModel: NewsMainViewModel
) {
    val navigationState = rememberNavigationState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                actions = {
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier=Modifier.padding(end = 6.dp),
                            painter = painterResource(R.drawable.newslogo_1),
                            contentDescription = null
                        )
                        Text(
                            color = Color.Black,
                            style = textStyles.h1Bold,
                            text = "News 24"
                        )
                    }

                }

            )
        },


        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Search,
                    NavigationItem.Favourite
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(route = item.screen.route)
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(text = item.titleResId) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onBackground,
                            selectedTextColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }

    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,

            articleListContent = {
                NewsMainScreenInternal(
                    viewModel = viewModel,
                    modifier = modifier.padding(paddingValues),
                    textStyles = textStyles
                )
            },
            favouriteScreenContent = { Text("Favourite") },
            searchScreenContent = { Text("Search") }
        )
    }
}

@Composable
internal fun NewsMainScreenInternal(
    viewModel: NewsMainViewModel,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    NewsMainContent(currentState, modifier, textStyles)
}

@Composable
private fun NewsMainContent(
    currentState: State,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    Column(modifier) {
        when (currentState) {
            is State.Success -> ArticleList(currentState, textStyles = textStyles)
            is State.Error -> ErrorMessage(currentState)
            is State.Loading -> ProgressIndicator(currentState, textStyles = textStyles)
            State.None -> Unit
        }
    }
}

@Composable
private fun ErrorMessage(state: State.Error) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = MaterialTheme.colorScheme.onError)
        }
    }
}


@Composable
private fun ProgressIndicator(
    state: State.Loading,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    Column {
        Box(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        val articles = state.articles
        if (articles != null) {
            ArticleList(articles, modifier, textStyles)
        }
    }
}


