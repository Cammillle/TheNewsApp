package com.example.news.data

import com.example.news.data.models.Article
import com.example.news.database.NewsDatabase
import com.example.news.database.models.ArticleDBO
import com.example.newsapi.NewsApi
import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val database: NewsDatabase,
    private val api: NewsApi,
) {

    /**
     * Получение актуальных новостей с отслеживанием состояния запроса ("Обновляется", "Успешно", "Ошибка")
     */
    fun getAll(
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy()
    ): Flow<RequestResult<List<Article>>> {

        val cachedArticles = getAllFromDatabase()
        val remoteArticles = getAllFromServer()

        return cachedArticles
            .combine(remoteArticles) { dbos: RequestResult<List<Article>>, dtos: RequestResult<List<Article>> ->
                mergeStrategy.merge(dbos, dtos)
            }
    }

    private fun getAllFromServer(): Flow<RequestResult<List<Article>>> {
        val apiRequest = flow {
            emit(api.getEverything()) //Result<ResponseDTO<ArticleDTO>>
        }.onEach { result ->
            // Если запрос прошел успешно, сохраняем данные в локальный кэш (БД)
            if (result.isSuccess) {
                saveNetResponseToCache(checkNotNull(result.getOrThrow().articles))
            }
        }.map {
            it.toRequestResult()
        }

        val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())

        return merge(apiRequest, start)
            .map { result ->
                result.map { response ->
                    response.articles.map { it.toArticle() }
                }
            }
    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDTO -> articleDTO.toArticleDBO() }
        database.articleDao().insert(dbos)
    }

    private fun getAllFromDatabase(): Flow<RequestResult<List<Article>>> {
        val dbRequest = database.articleDao()
            .getAll() //Flow<List<ArticleDBO>>
            .map { RequestResult.Success(it) }

        val start = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(start, dbRequest).map { result ->
            result.map { dbos ->
                dbos.map { it.toArticle() }
            }
        }
    }

    suspend fun search(query: String): Flow<Article> {
        api.getEverything()
        TODO()
    }
}

