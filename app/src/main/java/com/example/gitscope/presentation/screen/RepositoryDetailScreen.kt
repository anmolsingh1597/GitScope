package com.example.gitscope.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitscope.data.model.Repository
import com.example.gitscope.presentation.components.DetailRow
import com.example.gitscope.presentation.navigation.RepositoryNavArgs
import com.example.gitscope.presentation.ui.theme.GitScopeTheme
import com.example.gitscope.presentation.util.extensions.formatDate
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RepositoryDetailScreen(
    repositoryNavArgs: RepositoryNavArgs,
    onDismiss: () -> Unit,
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Repository Details",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = repositoryNavArgs.repository.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = repositoryNavArgs.repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            DetailRow(
                label = "Forks",
                value = repositoryNavArgs.repository.forksCount.toString()
            )
            DetailRow(
                label = "Stars",
                value = repositoryNavArgs.repository.stargazersCount.toString()
            )
            DetailRow(
                label = "Updated",
                value = repositoryNavArgs.repository.updatedAt.formatDate()
            )

            HorizontalDivider()

            val totalForksColor = if (repositoryNavArgs.totalForks > 5000) {
                Color(0xFFFFCC00)
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            val totalForksText = if (repositoryNavArgs.totalForks > 5000) {
                "⭐ Total Forks: ${repositoryNavArgs.totalForks}"
            } else {
                "Total Forks: ${repositoryNavArgs.totalForks}"
            }

            Text(
                text = totalForksText,
                style = MaterialTheme.typography.titleMedium,
                color = totalForksColor,
                fontWeight = if (repositoryNavArgs.totalForks > 5000) FontWeight.Bold else FontWeight.Normal
            )
        }
    }

}

@Preview(name = "Light", group = "themes")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, group = "themes")
@Composable
fun RepositoryDetailScreenThemePreview() {
    GitScopeTheme {
        RepositoryDetailScreen(
            repositoryNavArgs = RepositoryNavArgs(
                repository = Repository(
                    name = "Hello-World",
                    description = "This your first repo!",
                    forksCount = 42,
                    updatedAt = "2024-01-15T10:30:00Z",
                    stargazersCount = 123,
                ),
                totalForks = 5000
            ),
            onDismiss = {},
        )
    }
}

@Preview(name = "Popular Repository")
@Composable
fun RepositoryDetailScreenPopularPreview() {
    GitScopeTheme {
        RepositoryDetailScreen(
            repositoryNavArgs = RepositoryNavArgs(
                repository = Repository(
                    name = "Hello-World",
                    description = "This your first repo!",
                    forksCount = 42,
                    updatedAt = "2024-01-15T10:30:00Z",
                    stargazersCount = 123,
                ),
                totalForks = 5000
            ),
            onDismiss = {},
        )
    }
}