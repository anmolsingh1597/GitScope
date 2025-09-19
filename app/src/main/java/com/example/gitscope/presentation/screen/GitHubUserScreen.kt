package com.example.gitscope.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User
import com.example.gitscope.presentation.components.SearchSection
import com.example.gitscope.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubUserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showRepositoryDetails by remember { mutableStateOf<Repository?>(null) }

    GitHubUserScreen(
        title = "GitHub User",
        searchQuery = uiState.searchQuery,
        updateSearchQuery = viewModel::updateSearchQuery,
        onSearchClick = viewModel::searchUser,
        isLoading = uiState.isLoading,
        user = uiState.user,
        repositories = uiState.repositories,
        totalForks = uiState.totalForks,
        error = uiState.error,
        showRepositoryDetails = showRepositoryDetails,
    )
}

@Composable
fun GitHubUserScreen(
    modifier: Modifier = Modifier,
    title: String,
    searchQuery: String,
    updateSearchQuery: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    isLoading: Boolean = false,
    user: User?,
    repositories: List<Repository> = emptyList<Repository>(),
    totalForks: Int,
    error: String?,
    showRepositoryDetails: Repository?
){
    LaunchedEffect(Unit) {

    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ){
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        SearchSection(
            searchQuery = searchQuery,
            onSearchQueryChange = updateSearchQuery,
            onSearchClick = onSearchClick,
            isLoading = isLoading
        )
    }

}

@Preview
@Composable
fun GithubUserScreenPreview(){
    GitHubUserScreen(
        title = "GitHub User",
        searchQuery = "",
        isLoading = false,
        user = User(
            avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
            name = "The Octocat",
        ),
        totalForks = 0,
        error = "",
        showRepositoryDetails = Repository(
            name = "Hello-World",
            description = "This your first repo!",
            forksCount = 0,
            updatedAt = "2023-01-01T00:00:00Z",
            stargazersCount = 0,
        ),
    )

}