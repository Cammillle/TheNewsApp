package com.example.newsapi

import com.example.newsapi.models.ArticleDTO
import com.example.newsapi.models.LanguageDTO
import com.example.newsapi.models.ResponseDTO
import com.example.newsapi.models.SortByDTO
import com.example.newsapi.utils.NewsApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date


interface NewsApi {

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("language") languages: List<@JvmSuppressWildcards LanguageDTO>? = null,
        @Query("sortBy") sortBy: SortByDTO = SortByDTO.popularity,
        @Query("pageSize") pageSize: Int = 100,
        @Query("page") page: Int = 1
    ): Result<ResponseDTO<ArticleDTO>>
}

fun NewsApi(
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

    val modifiedOkHttpClient: OkHttpClient =
        (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
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