package com.example.gitscope.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.util.extensions.formatDate
import com.example.gitscope.presentation.components.DetailRow
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailScreen(
    repository: Repository,
    totalForks: Int,
    onDismiss: () -> Unit
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
        )
        {
            Text(
                text = repository.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            DetailRow(
                label = "Forks",
                value = repository.forksCount.toString()
            )
            DetailRow(
                label = "Stars",
                value = repository.stargazersCount.toString()
            )
            DetailRow(
                label = "Updated",
                value = repository.updatedAt.formatDate()
            )

            HorizontalDivider()

            val totalForksColor = if (totalForks > 5000) {
                Color(0xFFFFCC00)
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            val totalForksText = if (totalForks > 5000) {
                "⭐ Total Forks: $totalForks"
            } else {
                "Total Forks: $totalForks"
            }

            Text(
                text = totalForksText,
                style = MaterialTheme.typography.titleMedium,
                color = totalForksColor,
                fontWeight = if (totalForks > 5000) FontWeight.Bold else FontWeight.Normal
            )

        }
    }

}

@Preview
@Composable
fun RepositoryDetailScreenPreview() {
    MaterialTheme {
        RepositoryDetailScreen(
            repository = Repository(
                name = "Hello-World",
                description = "This your first repo!",
                forksCount = 0,
                updatedAt = "2023-01-01T00:00:00Z",
                stargazersCount = 0,
            ),
            totalForks = 65000,
            onDismiss = {}
        )
    }

}