package com.example.news.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.news.data.models.Source
import com.example.news.main.AppTextStyles
import com.example.news.main.ArticleUI
import com.example.news.main.State

@Composable
internal fun ArticleList(
    articleState: State.Success,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    ArticleList(articles = articleState.articles, modifier = modifier, textStyles)
}

@Composable
internal fun ArticleList(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1) articles: List<ArticleUI>,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    LazyColumn(modifier) {
        items(articles) { article ->
            key(article.id) {
                Article(article, textStyles = textStyles)
            }
        }
    }
}

@Composable
internal fun Article(
    @PreviewParameter(ArticlePreviewProvider::class, limit = 1) article: ArticleUI,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles
) {
    Row(
        modifier.padding(bottom = 4.dp)
    ) {
        article.imageUrl?.let { imageUrl ->
            var isImageVisible by remember { mutableStateOf(true) }
            if (isImageVisible) {
                AsyncImage(
                    model = imageUrl,
                    onState = { state: AsyncImagePainter.State ->
                        if (state is AsyncImagePainter.State.Error) {
                            isImageVisible = false
                        }
                    },
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val title = article.title
            if (title != null) {
                Text(
                    //modifier = Modifier.height(80.dp),
                    text = title,
                    style = textStyles.h2Bold,
                    maxLines = 4
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            val author = article.author
            if (author != null) {
                Text(
                    text = "By $author",
                    color = Color.Gray,
                    style = textStyles.h3Medium,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            val source = article.source
            if (source != null) {
                Text(
                    text = source.name!!,
                    color = Color(0xFF69BDFD),
                    style = textStyles.h3Bold,
                    maxLines = 1
                )
            }
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
                imageUrl = "https://media.wired.com/photos/68aea51fcb3116c38839e107/191:100/w_1280,c_limit/Google%20Pixel%2010%20Pro%20and%20Pixel%2010%20Pro%20XL%20SOURCE%20Julian%20Chokkattu.png",
                url = "",
                author = "Julian Chokkattu",
                source = Source(id = "wired", name = "Wired")
            ), ArticleUI(
                2,
                "Gemini 1.5 Release",
                "Upgraded version of Google AI is available",
                imageUrl = null,
                url = "",
                author = "Julian Chokkattu",
                source = Source(id = "wired", name = "Wired")
            ), ArticleUI(
                3,
                "Shape animations (10 min)",
                "How to use shape transform animations in Compose",
                imageUrl = null,
                url = "",
                author = "Julian Chokkattu",
                source = Source(id = "wired", name = "Wired")
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
