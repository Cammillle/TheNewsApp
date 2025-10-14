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

class ArticlesRepository(
    private val database: NewsDatabase, private val api: NewsApi
) {

    fun getAll(): Flow<RequestResult<List<Article>>> {
        val cachedArticles = getAllFromDatabase()
            .map { result ->
                result.map { dbos ->
                    dbos.map { it.toArticle() }
                }
            }

        val remoteArticles = getAllFromServer()
            .map { result ->
                result.map { response ->
                    response.articles.map { it.toArticle() }
                }
            }

        return cachedArticles.combine(remoteArticles) { dbos: RequestResult<List<Article>>, dtos: RequestResult<List<Article>> ->
            RequestResult.InProgress()
        }
    }

    private fun getAllFromServer(): Flow<RequestResult<ResponseDTO<ArticleDTO>>> {
        val apiRequest = flow {
            emit(api.getEverything())
        }.onEach { result ->
            if (result.isSuccess) {
                saveNetResponseToCache(checkNotNull(result.getOrThrow().articles))
            }
        }.map {
            it.toRequestResult()
        }

        val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())

        return merge(apiRequest, start)
    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDTO -> articleDTO.toArticleDBO() }
        database.articleDao().insert(dbos)
    }

    private fun getAllFromDatabase(): Flow<RequestResult.Success<List<ArticleDBO>>> {
        return database.articleDao().getAll()
            /**Flow<List<ArticleDBO>>*/
            .map { RequestResult.Success(it) }
    }

    suspend fun search(query: String): Flow<Article> {
        api.getEverything()
        TODO()
    }
}

sealed class RequestResult<E>(internal val data: E? = null) {
    class InProgress<E>(data: E? = null) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)
    class Error<E>(data: E? = null) : RequestResult<E>()
}

internal fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.Success -> {
            val outData: O = mapper(checkNotNull(data))
            RequestResult.Success(checkNotNull(outData))
        }

        is RequestResult.Error -> RequestResult.Error(data?.let(mapper))

        is RequestResult.InProgress -> RequestResult.InProgress(data?.let(mapper))
    }
}

internal fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error()
        else -> error("Impossible branch")
    }
}