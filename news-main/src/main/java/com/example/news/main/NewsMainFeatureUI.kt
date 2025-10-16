package com.example.news.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMainScreen(modifier: Modifier = Modifier) {
    NewsMainScreen(viewModel = viewModel(), modifier = modifier)
}

@Composable
internal fun NewsMainScreen(
    viewModel: NewsMainViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    NewsMainContent(currentState, modifier)
}

@Composable
private fun NewsMainContent(
    currentState: State,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        when (currentState) {
            is State.Success -> Articles(currentState.articles)
            is State.Error -> ErrorMessage(currentState)
            is State.Loading -> ProgressIndicator(currentState)
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
@Preview(showBackground = true)
private fun Articles(
    @PreviewParameter(
        ArticlesPreviewProvider::class, limit = 1
    ) articles: List<ArticleUI>?
) {
    LazyColumn {
        if (articles != null) {
            items(articles) { article ->
                key(article.id) {
                    Article(article)
                }
            }
        }

    }
}

@Composable
private fun ProgressIndicator(state: State.Loading) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        val articles = state.articles
        if (articles != null) {
            Articles(articles)
        }
    }
}


@Composable
@Preview
private fun Article(
    @PreviewParameter(
        ArticlePreviewProvider::class, limit = 1
    ) article: ArticleUI
) {
    Column {
        val title = article.title
        if (title != null) {
            Text(
                text = article.title, style = MaterialTheme.typography.headlineMedium, maxLines = 1
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        val desc = article.description
        if (desc != null) {
            Text(
                text = desc, style = MaterialTheme.typography.headlineMedium, maxLines = 1
            )
        }
    }
}

class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI> {

    override val values: Sequence<ArticleUI>
        get() = sequenceOf(
            ArticleUI(
                1,
                "Android Studio Iguana is Stable!",
                "New stable version on Android IDE has been release",
                imageUrl = null,
                url = ""
            ), ArticleUI(
                2,
                "Gemini 1.5 Release",
                "Upgraded version of Google AI is available",
                imageUrl = null,
                url = ""
            ), ArticleUI(
                3,
                "Shape animations (10 min)",
                "How to use shape transform animations in Compose",
                imageUrl = null,
                url = ""
            )
        )
}

class ArticlesPreviewProvider() : PreviewParameterProvider<List<ArticleUI>> {
    private val provider = ArticlePreviewProvider()
    override val values: Sequence<List<ArticleUI>>
        get() = sequenceOf(
            provider.values.toList()
        )
}
