package com.example.news.main

import com.example.news.data.RequestResult
import com.example.news.data.models.Article


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
        url = url
    )
}
