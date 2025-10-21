package com.example.news.main.ui.articleList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.news.data.models.Source
import com.example.news.main.utils.AppTextStyles
import com.example.news.main.ArticleUI
import com.example.news.main.State
import com.example.news.main.utils.formatTimeDifference
import java.util.Date


@Composable
internal fun ArticleList(
    articleState: State.Success,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles,
    onArticleClickListener: (ArticleUI) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    ArticleList(
        articles = articleState.articles,
        modifier = modifier,
        textStyles = textStyles,
        onArticleClickListener = onArticleClickListener,
        onCategoryChange = onCategoryChange
    )
}

@Composable
internal fun ArticleList(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1)
    articles: List<ArticleUI>,
    modifier: Modifier = Modifier,
    textStyles: AppTextStyles,
    onArticleClickListener: (ArticleUI) -> Unit,
    onCategoryChange: (String) -> Unit
) {
    Column(modifier.padding(top = 10.dp)) {
        ChipsMenu(
            textStyles,
            onCategoryChange = onCategoryChange
        )
    }

    LazyColumn(modifier.padding(top = 10.dp)) {
        items(articles) { article ->
            key(article.id) {
                Article(
                    article,
                    textStyles = textStyles,
                    onArticleClickListener = {
                        onArticleClickListener(article)
                    })
            }
        }
    }
}

@Composable
internal fun Article(
    article: ArticleUI,
    textStyles: AppTextStyles,
    modifier: Modifier = Modifier,
    onArticleClickListener: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
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
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        /***Блок с контентом*/
        Column(
            modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .padding(vertical = 4.dp)
        ) {
            article.title?.let { title ->
                Text(
                    text = title,
                    style = textStyles.h2Bold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            article.author?.let { author ->
                Text(
                    text = "By $author",
                    color = Color.Gray,
                    style = textStyles.h3Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            /**Метаданные**/
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    article.source?.name?.let { name ->
                        Text(
                            text = name,
                            color = Color(0xFF69BDFD),
                            style = textStyles.h3Bold,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "●",
                            style = textStyles.p1Regular,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    article.publishedAt?.let { publishedAt ->
                        val timePassed = formatTimeDifference(
                            publishedDate = article.publishedAt, currentDate = Date()
                        )
                        Text(
                            text = timePassed, style = textStyles.h3Medium, color = Color.Gray
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = { onArticleClickListener() },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More options",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    )
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
                source = Source(id = "wired", name = "Wired"),
                publishedAt = Date("2025-10-07T14:23:13Z".toLong()),
                content = ""
            ), ArticleUI(
                2,
                "Gemini 1.5 Release",
                "Upgraded version of Google AI is available",
                imageUrl = null,
                url = "",
                author = "Julian Chokkattu",
                source = Source(id = "wired", name = "Wired"),
                publishedAt = Date("2025-10-07T14:23:13Z".toLong()),
                content = ""
            ), ArticleUI(
                3,
                "Shape animations (10 min)",
                "How to use shape transform animations in Compose",
                imageUrl = null,
                url = "",
                author = "Julian Chokkattu",
                source = Source(id = "wired", name = "Wired"),
                publishedAt = Date("2025-10-07T14:23:13Z".toLong()),
                content = ""
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
