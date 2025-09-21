package com.example.gitscope.presentation.viewmodel

import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User
import com.example.gitscope.data.remote.Result
import com.example.gitscope.domain.model.UserWithRepositories
import com.example.gitscope.domain.usecase.CalculateTotalForkUseCase
import com.example.gitscope.domain.usecase.GetUserRepositoryUseCase
import com.example.gitscope.domain.usecase.GetUserUseCase
import com.example.gitscope.domain.usecase.GetUserWithRepositoriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val mockGetUserUseCase = mockk<GetUserUseCase>()
    private val mockGetUserRepositoryUseCase = mockk<GetUserRepositoryUseCase>()
    private val mockGetUserWithRepositoriesUseCase = mockk<GetUserWithRepositoriesUseCase>()
    private val mockCalculateTotalForkUseCase = mockk<CalculateTotalForkUseCase>()

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel = UserViewModel(
            mockGetUserUseCase,
            mockGetUserRepositoryUseCase,
            mockGetUserWithRepositoriesUseCase,
            mockCalculateTotalForkUseCase,
            testDispatcher
        )

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchUser_updates_UI_state_correctly() = testScope.runTest {
        val expectedResult = Result.Success(
            UserWithRepositories(
                user = User("octocat", "https://avatars.githubusercontent.com/u/583231?v=4"),
                repositories = listOf(
                    Repository("repo1", "desc1", "2025-09-20T00:00:00Z", 100, 100),
                    Repository("repo2", "desc2", "2025-09-20T00:00:00Z", 200, 200)
                ),
                totalForks = 300
            )
        )

        coEvery { mockGetUserWithRepositoriesUseCase("octocat") } returns expectedResult

        viewModel.searchUser("octocat")

        advanceUntilIdle()

        assertEquals(expectedResult.data.user, viewModel.uiState.value.user)
        assertEquals(expectedResult.data.repositories, viewModel.uiState.value.repositories)
        assertEquals(expectedResult.data.totalForks, viewModel.uiState.value.totalForks)
    }
}