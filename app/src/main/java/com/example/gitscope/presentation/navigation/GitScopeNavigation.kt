package com.example.gitscope.presentation.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitscope.data.model.Repository
import com.example.gitscope.presentation.screen.GitHubUserScreen
import com.example.gitscope.presentation.screen.RepositoryDetailScreen
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryNavArgs(
    val repository: Repository,
    val totalForks: Int
) : Parcelable


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
                onNavigateToRepositoryDetail = { navArgs ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("navArgs", navArgs)
                    navController.navigate(GitScopeRoutes.REPOSITORY_DETAIL)
                }
            )
        }

        composable(GitScopeRoutes.REPOSITORY_DETAIL) {
            val navArgs = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RepositoryNavArgs>("navArgs")

            RepositoryDetailScreen(
                repositoryNavArgs = navArgs ?: RepositoryNavArgs(
                    repository = Repository(
                        name = "Hello-World",
                        description = "This your first repo!",
                        forksCount = 42,
                        updatedAt = "2024-01-15T10:30:00Z",
                        stargazersCount = 123,
                    ),
                    totalForks = 5000
                ),
                onDismiss = { navController.popBackStack() },
                enableAnimation = false,
            )
        }
    }
}