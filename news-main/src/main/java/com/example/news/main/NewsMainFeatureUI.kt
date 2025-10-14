package com.example.news.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMain() {
    NewsMain(viewModel = viewModel())
}

@Composable
internal fun NewsMain(
    viewModel: NewsMainViewModel
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is State.Success -> DrawContent(currentState)
        is State.Error -> TODO()
        is State.Loading -> TODO()
        State.None -> TODO()
    }

}

@Composable
private fun DrawContent(
    state: State.Success
) {


}