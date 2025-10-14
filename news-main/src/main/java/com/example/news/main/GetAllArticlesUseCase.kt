package com.example.news.main

import com.example.news.data.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(
    private val repository: ArticlesRepository
) {
     operator fun invoke(): Flow<List<Article>> {
        return repository.getAll()
    }
}