package com.example.news.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.news.main.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: String,
    val icon: ImageVector
) {

    object Home : NavigationItem(
        screen = Screen.Home,
        titleResId = "Home",
        icon = Icons.Outlined.Home
    )

    object Search : NavigationItem(
        screen = Screen.Search,
        titleResId = "Search",
        icon = Icons.Outlined.Search
    )

    object Favourite : NavigationItem(
        screen = Screen.Favourite,
        titleResId = "Favourite",
        icon = Icons.Outlined.BookmarkBorder
    )
}