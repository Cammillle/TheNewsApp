package com.example.news.main.ui.articlePost

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.news.main.AppTextStyles
import com.example.news.main.ArticleUI

@Composable
fun ArticlePostContent(
    article: ArticleUI,
    textStyles: AppTextStyles,
    modifier: Modifier = Modifier
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
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
                            .height(220.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }

        Column(
            modifier.padding(vertical = 16.dp)
        ) {

            /***Title*/
            article.title?.let { title ->
                Text(
                    text = title,
                    style = textStyles.h2Bold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }


            Row(
                modifier.padding(bottom = 20.dp)
            ) {
                Text("By Andy Corlbey")
                Text("1m ago")
            }

            /**Контент*/

            article.content?.let { content ->
                Text(content)
            }
        }
    }

}