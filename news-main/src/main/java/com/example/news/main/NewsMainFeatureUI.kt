package com.example.news.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMainScreen() {
    NewsMainScreen(viewModel = viewModel())
}

@Composable
internal fun NewsMainScreen(
    viewModel: NewsMainViewModel
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is State.Success -> Articles(currentState.articles)
        is State.Error -> ErrorMessage()
        is State.Loading -> Articles(currentState.articles)
        State.None -> Unit
    }
}

@Composable
private fun ErrorMessage() {
}

@Composable
@Preview(showBackground = true)
private fun Articles(
    @PreviewParameter(
        ArticlesPreviewProvider::class, limit = 1
    ) articles: List<ArticleUI>
) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

class ArticlesPreviewProvider() : PreviewParameterProvider<List<ArticleUI>> {
    private val provider = ArticlePreviewProvider()
    override val values: Sequence<List<ArticleUI>>
        get() = sequenceOf(
            provider.values.toList()
        )
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
