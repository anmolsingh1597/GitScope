package com.example.gitscope.presentation.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitscope.presentation.ui.theme.GitScopeTheme

@Composable
fun RecentSearchSection(
    recentSearches: List<String> = emptyList(),
    updateSearchQuery: (String) -> Unit = {},
) {

    val suggestions = listOf<String>(
        "octocat",
        "google",
        "anmolsingh1597"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if(recentSearches.isEmpty()) { "Suggestions" }else{"Recent Searches"},
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(recentSearches.ifEmpty { suggestions }) { searchQuery ->
                Card(
                    onClick = { updateSearchQuery(searchQuery) },
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = searchQuery,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(10.dp)
                    )

                }
            }
        }
    }
}


@Preview()
@Composable
fun RecentSearchSectionPreview() {
    GitScopeTheme (
    ){
        RecentSearchSection(
            recentSearches = listOf(
                "octocat",
                "google",
                "anmolsingh1597"
            ),
            updateSearchQuery = {}
        )
    }
}