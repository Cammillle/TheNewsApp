package com.example.newsapp

import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.news.common.AndroidLogcatLogger
import com.example.news.common.AppDispatchers
import com.example.news.common.Logger
import com.example.news.database.NewsDatabase
import com.example.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(
    ): NewsApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun provideLogger(): Logger = AndroidLogcatLogger()

    @Provides
    @Singleton
    fun provideFontFamily(@ApplicationContext context: Context): FontFamily {
        return FontFamily(
            Font(R.font.sf_pro_bold, FontWeight.Bold),
            Font(R.font.sf_pro_medium, FontWeight.Medium),
            Font(R.font.sf_pro_regular, FontWeight.Normal),
            Font(R.font.sf_pro_regularitalic, FontWeight.Normal, FontStyle.Italic)
        )
    }

}