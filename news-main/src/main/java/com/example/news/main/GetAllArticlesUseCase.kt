package com.example.news.main

import com.example.news.data.ArticlesRepository
import com.example.news.data.RequestResult
import com.example.news.data.map
import com.example.news.main.utils.toUiArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {
    operator fun invoke(query: String): Flow<RequestResult<List<ArticleUI>>> {
        return repository
            .getAll(query)
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map {
                        it.toUiArticle()
                    }
                }
            }
    }
}

