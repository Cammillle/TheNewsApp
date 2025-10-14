package com.example.newsapi

import com.example.newsapi.models.Article
import com.example.newsapi.models.Language
import com.example.newsapi.models.Response
import com.example.newsapi.models.SortBy
import com.example.newsapi.utils.NewsApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date


interface NewsApi {

    @GET("/everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("language") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") pageSize: Int = 100,
        @Query("page") page: Int = 1
    ): Result<Response<Article>>
}

fun newsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null
): NewsApi {
    val retrofit = retrofit(baseUrl, apiKey, okHttpClient)
    return retrofit.create(NewsApi::class.java)
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?
): Retrofit {

    val modifiedOkHttpClient: OkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apiKey))
        .build()

    val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(modifiedOkHttpClient)
            .build()
    return retrofit
}