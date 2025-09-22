package com.example.gitscope.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitscope.data.model.Repository
import com.example.gitscope.presentation.screen.GitHubUserScreen
import com.example.gitscope.presentation.screen.RepositoryDetailScreen


object GitScopeRoutes {
    const val HOME = "home"
    const val REPOSITORY_DETAIL = "repository_detail"
}

@Composable
fun GitScopeNavigation (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = GitScopeRoutes.HOME
)
{
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier
    ) {
        composable(GitScopeRoutes.HOME) {
            GitHubUserScreen(
                onNavigateToRepositoryDetail = { repository ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("repository", repository)
                    navController.navigate(GitScopeRoutes.REPOSITORY_DETAIL)
                }
            )
        }

        composable(GitScopeRoutes.REPOSITORY_DETAIL) {
            val repository = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Repository>("repository")
            RepositoryDetailScreen(
                repository = repository ?: Repository(
                    name = "Dummy Repo",
                    description = "This is dummy repo!",
                    forksCount = 42,
                    updatedAt = "2024-01-15T10:30:00Z",
                    stargazersCount = 123,
                ),
                onDismiss = { navController.popBackStack() },
                enableAnimation = false
            )
        }
    }
}