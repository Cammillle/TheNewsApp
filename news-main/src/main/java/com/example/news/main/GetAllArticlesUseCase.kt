package com.example.news.main

import com.example.news.data.ArticlesRepository
import com.example.news.data.RequestResult
import com.example.news.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllArticlesUseCase(
    private val repository: ArticlesRepository
) {
    operator fun invoke(): Flow<RequestResult<List<Article>>> {
        return repository
            .getAll()
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map {
                        it.toUiArticle()
                    }
                }
            }
    }
}

private fun com.example.news.data.models.Article.toUiArticle(): Article {
    TODO("Not yet implemented")
}
