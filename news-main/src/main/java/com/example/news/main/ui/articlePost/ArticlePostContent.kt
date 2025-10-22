package com.example.news.main.ui.articlePost

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.news.main.utils.AppTextStyles
import com.example.news.main.ArticleUI
import com.example.news.main.R
import com.example.news.main.utils.formatTimeDifference
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlePostContent(
    article: ArticleUI,
    textStyles: AppTextStyles,
    modifier: Modifier,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.outline_bookmark_24),
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.outline_ios_share_24),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.outline_arrow_back_ios_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        /**Scrollable Column*/
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            /**AsyncImage*/
            Row(
                modifier = Modifier
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
                                .height(220.dp)
                            //.clip(MaterialTheme.shapes.medium)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {

                /***Title*/
                article.title?.let { title ->
                    Text(
                        text = title,
                        style = textStyles.articleTitle,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                /**Author*/
                Row(
                    modifier = Modifier.padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.author_pic),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    article.author?.let {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "By $it",
                            style = textStyles.h3Medium,
                            color = Color.Gray,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    article.publishedAt?.let {
                        Text(
                            text = formatTimeDifference(publishedDate = it, currentDate = Date()),
                            style = textStyles.h3Medium,
                            color = Color.Gray,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                /**Контент*/
                article.content?.let { content ->
                    Text(
                        text = content,
                        style = textStyles.actionSheet,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}