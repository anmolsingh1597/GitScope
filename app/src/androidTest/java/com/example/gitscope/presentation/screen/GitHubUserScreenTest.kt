package com.example.gitscope.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gitscope.data.model.User
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitHubUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchSection_is_displayed_when_isLoading_is_false() {
        composeTestRule.setContent {
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
            )
        }

        composeTestRule
            .onNodeWithText("GitHub Username")
            .assertIsDisplayed()
    }
}