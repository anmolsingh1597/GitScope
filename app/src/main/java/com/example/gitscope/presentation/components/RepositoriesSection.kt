package com.example.gitscope.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitscope.data.model.Repository

@Composable
fun RepositoriesSection(
    repositories: List<Repository>,
    onRepositoryClick: (Repository) -> Unit = {}
){
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = if(repositories.isEmpty()) "No repositories found" else "Repositories (${repositories.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 8.dp
            )
        )

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(repositories){ repository ->
                RepositoryItem(
                    repository = repository,
                    onClick = { onRepositoryClick(repository) }
                )
            }
        }
    }
}

@Preview
@Composable
fun RepositoriesSectionPreview(){
    RepositoriesSection(
        repositories = listOf(
            Repository(
                name = "Hello-World",
                description = "This your first repo!",
                forksCount = 0,
                updatedAt = "2023-01-01T00:00:00Z",
                stargazersCount = 0,
            ),
            Repository(
                name = "Hello-World",
                description = "This your first repo!",
                forksCount = 0,
                updatedAt = "2023-01-01T00:00:00Z",
                stargazersCount = 0,
                )
        )

    )

}