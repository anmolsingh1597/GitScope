package com.example.gitscope.presentation.screen

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User
import com.example.gitscope.presentation.components.ErrorCard
import com.example.gitscope.presentation.sections.RecentSearchSection
import com.example.gitscope.presentation.sections.RepositoriesSection
import com.example.gitscope.presentation.sections.SearchSection
import com.example.gitscope.presentation.sections.UserSection
import com.example.gitscope.presentation.ui.theme.GitScopeTheme
import com.example.gitscope.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubUserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        clearError = viewModel::clearError,
        clearSession = viewModel::clearUiState,
        recentSearches = uiState.recentSearches
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
    clearError: () -> Unit = {},
    clearSession: () -> Unit = {},
    recentSearches: List<String> = emptyList()
) {
    var showRepositoryDetails by remember { mutableStateOf<Repository?>(null) }

    showRepositoryDetails?.let { repository ->
        RepositoryDetailScreen(
            repository = repository,
            totalForks = totalForks,
            onDismiss = { showRepositoryDetails = null }
        )

        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = clearSession
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(16.dp),
        )
        {
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

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = error != null,
                enter = fadeIn(animationSpec = tween(300)) +
                        slideInVertically(initialOffsetY = { it / 4 }),
                exit = fadeOut() + slideOutVertically()
            ) {
                error?.let { errorMessage ->
                    ErrorCard(
                        error = errorMessage,
                        onDismiss = clearError,
                    )
                }
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(
                visible = isLoading,
                enter = fadeIn(animationSpec = tween(300)) +
                        slideInVertically(initialOffsetY = { it / 4 }),
                exit = fadeOut() + slideOutVertically()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            user?.let { userInfo ->
                AnimatedVisibility(
                    visible = !isLoading,
                    enter = fadeIn(animationSpec = tween(300)) +
                            slideInVertically(initialOffsetY = { it / 4 }),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Column {
                        UserSection(user = userInfo)
                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(
                            visible = repositories.isNotEmpty(),
                            enter = scaleIn(
                                initialScale = 0.8f,
                                animationSpec = tween(durationMillis = 400)
                            ) + fadeIn(animationSpec = tween(durationMillis = 400)),
                            exit = scaleOut(
                                targetScale = 0.8f,
                                animationSpec = tween(durationMillis = 300)
                            ) + fadeOut(animationSpec = tween(durationMillis = 300))
                        ) {
                            RepositoriesSection(
                                repositories = repositories,
                                onRepositoryClick = { repository ->
                                    showRepositoryDetails = repository
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = user == null && !isLoading,
                enter = fadeIn(animationSpec = tween(300)) +
                        slideInVertically(initialOffsetY = { it / 4 }),
                exit = fadeOut() + slideOutVertically()
            ) {
                RecentSearchSection(
                    recentSearches = recentSearches,
                    updateSearchQuery = updateSearchQuery
                )
            }
        }
    }
}

@Preview(name = "Light", group = "themes")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, group = "themes")
@Composable
fun GithubUserScreenThemePreview() {
    GitScopeTheme {
        GitHubUserScreen(
            title = "GitHub User",
            searchQuery = "octocat",
            isLoading = false,
            user = User(
                avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
                name = "The Octocat",
            ),
            totalForks = 1250,
            error = "",
        )
    }
}

@Preview(
    name = "Loading",
)
@Composable
fun GithubUserScreenLoadingPreview() {
    GitScopeTheme {
        GitHubUserScreen(
            title = "GitHub User",
            searchQuery = "octocat",
            isLoading = true,
            user = null,
            totalForks = 0,
            error = "",
        )
    }
}

@Preview(name = "Error State")
@Composable
fun GithubUserScreenErrorPreview() {
    GitScopeTheme {
        GitHubUserScreen(
            title = "GitHub User",
            searchQuery = "nonexistentuser",
            isLoading = false,
            user = null,
            totalForks = 0,
            error = "User not found",
        )
    }
}