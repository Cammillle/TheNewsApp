package com.example.news.main

sealed class State {
    object None : State()
    class Loading(val articles: List<ArticleUI>?) : State()
    class Error : State()
    class Success(val articles: List<ArticleUI>) : State()
}