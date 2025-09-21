package com.example.gitscope.presentation.uistate

import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User

data class UserUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val repositories: List<Repository> = emptyList(),
    val error: String? = null,
    val totalForks: Int = 0,
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList()
)

