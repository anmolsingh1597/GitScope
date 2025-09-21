package com.example.gitscope.presentation.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitscope.data.model.Repository
import com.example.gitscope.presentation.util.extensions.formatDate
import com.example.gitscope.presentation.components.DetailRow
import com.example.gitscope.presentation.ui.theme.GitScopeTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun RepositoryDetailScreen(
    repository: Repository,
    totalForks: Int,
    onDismiss: () -> Unit,
    enableAnimation: Boolean = true
) {
    var isVisible by remember { mutableStateOf(!enableAnimation) }
    var isExiting by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val handleDismiss: () -> Unit = {
        isExiting = true
        kotlinx.coroutines.GlobalScope.launch {
            delay(100)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible && !isExiting,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(400, easing = EaseOutCubic)
        ) + fadeIn(animationSpec = tween(400)),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300, easing = EaseInCubic)
        ) + fadeOut(animationSpec = tween(300))
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
                        IconButton(onClick = handleDismiss) {
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
}

@Preview(name = "Light", group = "themes")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, group = "themes")
@Composable
fun RepositoryDetailScreenThemePreview() {
    GitScopeTheme {
        RepositoryDetailScreen(
            repository = Repository(
                name = "Hello-World",
                description = "This your first repo!",
                forksCount = 42,
                updatedAt = "2024-01-15T10:30:00Z",
                stargazersCount = 123,
            ),
            totalForks = 5000,
            onDismiss = {},
            enableAnimation = false
        )
    }
}

@Preview(name = "Popular Repository")
@Composable
fun RepositoryDetailScreenPopularPreview() {
    GitScopeTheme {
        RepositoryDetailScreen(
            repository = Repository(
                name = "react",
                description = "The library for web and native user interfaces",
                forksCount = 45623,
                updatedAt = "2024-09-21T08:15:00Z",
                stargazersCount = 228000,
            ),
            totalForks = 125000,
            onDismiss = {},
            enableAnimation = false
        )
    }
}