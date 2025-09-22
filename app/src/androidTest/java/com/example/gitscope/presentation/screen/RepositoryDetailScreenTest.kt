package com.example.gitscope.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gitscope.data.model.Repository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repositoryDetailScreen_displays_repository_name(){
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repository = Repository(
                    name = "Test Repository",
                    description = "Test Description",
                    updatedAt = "2023-08-01T00:00:00Z",
                    stargazersCount = 10,
                    forksCount = 5),
                totalForks = 5,
                onDismiss = {},
                enableAnimation = false
            )
        }

        composeTestRule
            .onNodeWithText("Test Repository")
            .assertIsDisplayed()
    }

    @Test
    fun repositoryDetailScreen_displays_no_description_when_null() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repository = Repository(
                    name = "Test Repository",
                    description = null,
                    updatedAt = "2023-08-01T00:00:00Z",
                    stargazersCount = 10,
                    forksCount = 5),
                totalForks = 5000,
                onDismiss = {},
                enableAnimation = false
            )
        }

        composeTestRule
            .onNodeWithText("No description available")
            .assertIsDisplayed()
    }

    @Test
    fun repositoryDetailScreen_displays_regular_total_forks_when_under_threshold() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repository = Repository(
                    name = "Test Repository",
                    description = null,
                    updatedAt = "2023-08-01T00:00:00Z",
                    stargazersCount = 10,
                    forksCount = 5),
                totalForks = 3000,
                onDismiss = {},
                enableAnimation = false
            )
        }

        composeTestRule
            .onNodeWithText("Total Forks: 3000")
            .assertIsDisplayed()
    }

    @Test
    fun repositoryDetailScreen_displays_special_total_forks_when_over_threshold() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repository = Repository(
                    name = "Test Repository",
                    description = null,
                    updatedAt = "2023-08-01T00:00:00Z",
                    stargazersCount = 10,
                    forksCount = 5),
                totalForks = 6000,
                onDismiss = {},
                enableAnimation = false
            )
        }

        composeTestRule
            .onNodeWithText("⭐ Total Forks: 6000")
            .assertIsDisplayed()
    }
}