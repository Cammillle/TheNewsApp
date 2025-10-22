package com.example.news.main

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
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class NewsMainViewModel @Inject constructor(
    private val getAllArticlesUseCase: Provider<GetAllArticlesUseCase>
) : ViewModel() {
    private val _selectedCategory = MutableStateFlow("World")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<State> = _selectedCategory.flatMapLatest { query ->
        getAllArticlesUseCase.get().invoke(query)
            .map { it.toState() }
    }.stateIn(
        viewModelScope, SharingStarted.Lazily, State.None
    )

    fun changeCategory(query: String) {
        _selectedCategory.value = query
    }
}
