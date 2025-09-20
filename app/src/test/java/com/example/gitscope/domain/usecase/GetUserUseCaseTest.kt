package com.example.gitscope.domain.usecase

import com.example.gitscope.data.model.User
import com.example.gitscope.data.repository.GitHubScopeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.gitscope.data.remote.Result

@ExperimentalCoroutinesApi
class GetUserUseCaseTest {
    private val mockRepository = mockk<GitHubScopeRepository>()
    private val testDispatcher = StandardTestDispatcher()
    private val useCase = GetUserUseCase(mockRepository, testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke with empty userId returns Error` () = runTest {
        val result = useCase("")

        assertTrue(result is Result.Error)
        assertEquals("User ID cannot be empty", (result as Result.Error).message)
    }

    @Test
    fun `invoke with valid userId returns User` () = runTest {
        val expectedUser = User("octocat", "https://avatars.githubusercontent.com/u/583231?v=4")
        coEvery { mockRepository.getUser("octocat") } returns Result.Success(expectedUser)

        val result = useCase("octocat")
        assertTrue(result is Result.Success)
        assertEquals(expectedUser, (result as Result.Success).data)
    }

    @Test
    fun `invoke with lower case userId returns User`() = runTest {
        val expectedUser = User("octocat", "https://avatars.githubusercontent.com/u/583231?v=4")
        coEvery { mockRepository.getUser("OCTOCAT") } returns Result.Success(expectedUser)

        val result = useCase("OCTOCAT")
        assertTrue(result is Result.Success)
        assertEquals(expectedUser, (result as Result.Success).data)
    }

    @Test
    fun `invoke with trimmed userId returns User`() = runTest {
        val expectedUser = User("octocat", "https://avatars.githubusercontent.com/u/583231?v=4")
        coEvery { mockRepository.getUser("octocat") } returns Result.Success(expectedUser)

        val result = useCase(" octocat ")
        assertTrue(result is Result.Success)
        assertEquals(expectedUser, (result as Result.Success).data)
    }

}