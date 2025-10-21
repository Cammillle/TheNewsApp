package com.example.news.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.main.usecase.GetAllArticlesUseCase
import com.example.news.main.utils.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class NewsMainViewModel @Inject constructor(
    private val getAllArticlesUseCase: Provider<GetAllArticlesUseCase>
) : ViewModel() {
    private val currentQuery = MutableStateFlow("World")

    init {
        Log.d("ViewModel", "init")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<State> = currentQuery.flatMapLatest { query->
        getAllArticlesUseCase.get().invoke(query)
            .map { it.toState() }
    }.stateIn(
        viewModelScope, SharingStarted.Lazily, State.None
    )

//    val state: StateFlow<State> = getAllArticlesUseCase.get().invoke(query = "android")
//        .map { it.toState() }
//        .stateIn(viewModelScope, SharingStarted.Lazily, State.None)


    fun changeCategory(query: String) {
        currentQuery.value = query
        Log.d("ChipsMenu",query)
    }
}
