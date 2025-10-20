package com.example.news.main.utils

import com.example.news.data.RequestResult
import com.example.news.data.models.Article
import com.example.news.main.ArticleUI
import com.example.news.main.State


internal fun RequestResult<List<ArticleUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error()
        is RequestResult.Success -> State.Success(data!!)
        is RequestResult.InProgress -> State.Loading(data)
    }
}

internal fun Article.toUiArticle(): ArticleUI {
    return ArticleUI(
        id = id,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url,
        author = author,
        source = source,
        publishedAt = publishedAt,
        content = content
    )
}
