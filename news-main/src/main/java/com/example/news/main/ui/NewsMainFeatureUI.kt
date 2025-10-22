package com.example.news.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.news.main.utils.AppTextStyles
import com.example.news.main.ArticleUI
import com.example.news.main.NewsMainViewModel
import com.example.news.main.R
import com.example.news.main.State
import com.example.news.main.navigation.AppNavGraph
import com.example.news.main.navigation.rememberNavigationState
import com.example.news.main.ui.articleList.ArticleList
import com.example.news.main.ui.articleList.ChipsMenu
import com.example.news.main.ui.articlePost.ArticlePostContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsMainScreen(
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles,
    viewModel: NewsMainViewModel
) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(

        /***AppBar*/
        topBar = {
            CenterAlignedTopAppBar(
                actions = {
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 6.dp),
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

        /**BottomBar*/
        bottomBar = {
            val isArticlePostScreen =
                currentDestination?.route?.contains("article_post") == true

            if (!isArticlePostScreen) {
                NavigationBar {
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
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
        }

    ) { paddingValues ->

        /**AppNavGraph*/
        AppNavGraph(
            navHostController = navigationState.navHostController,

            articleListContent = {
                NewsMainScreenInternal(
                    viewModel = viewModel,
                    modifier = modifier.padding(paddingValues),
                    textStyles = textStyles,
                    onArticleClickListener = { articleUI ->
                        navigationState.navigateToArticlePost(articleUI)
                    },
                    onCategoryChange = { title ->
                        viewModel.changeCategory(title)
                    }
                )
            },
            articlePostContent = { article ->
                ArticlePostContent(
                    article,
                    textStyles = textStyles,
                    modifier = Modifier.padding(paddingValues)
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
    textStyles: AppTextStyles,
    onArticleClickListener: (ArticleUI) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    NewsMainContent(
        currentState,
        modifier,
        textStyles,
        onArticleClickListener = onArticleClickListener,
        onCategoryChange = onCategoryChange
    )
}

@Composable
private fun NewsMainContent(
    currentState: State,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles,
    onArticleClickListener: (ArticleUI) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    Column(modifier.padding(top = 10.dp)) {
        ChipsMenu(
            textStyles = textStyles,
            onCategoryChange = onCategoryChange
        )
        when (currentState) {
            is State.Success -> ArticleList(
                articleState = currentState,
                textStyles = textStyles,
                onArticleClickListener = onArticleClickListener,
            )

            is State.Error -> ErrorMessage(currentState)
            is State.Loading -> ProgressIndicator(
                currentState,
                textStyles = textStyles,
                onArticleClickListener = onArticleClickListener,
            )

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
    textStyles: AppTextStyles,
    onArticleClickListener: (ArticleUI) -> Unit
) {
    Column(modifier.padding(top = 10.dp)) {
        Box(
            modifier
                .fillMaxSize(),
                //.padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        val articles = state.articles
        if (articles != null) {
            ArticleList(
                articles,
                modifier,
                textStyles,
                onArticleClickListener = onArticleClickListener,
            )
        }
    }
}


