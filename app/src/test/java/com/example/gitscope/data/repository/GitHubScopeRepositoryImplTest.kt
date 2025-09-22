package com.example.gitscope.data.repository

import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.RepositoryResponse
import com.example.gitscope.data.model.User
import com.example.gitscope.data.model.UserResponse
import retrofit2.Response
import com.example.gitscope.data.remote.interfaces.ApiInterface
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.gitscope.data.remote.Result
import junit.framework.TestCase.assertEquals
import okhttp3.ResponseBody.Companion.toResponseBody

@ExperimentalCoroutinesApi
class GitHubScopeRepositoryImplTest {

    private val api: ApiInterface = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val repository = GitHubScopeRepositoryImpl(api, testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getUser returns User when response is successful`() = runTest {
        val userId = "octocat"
        val userResponse =
            UserResponse("octocat", "https://avatars.githubusercontent.com/u/583231?v=4")
        coEvery { api.getUser(userId) } returns Response.success(userResponse)

        val result = repository.getUser(userId)

        assert(result is Result.Success)
        assertEquals(User(userResponse.name ?: "", userResponse.avatarUrl), (result as Result.Success).data)
    }

    @Test
    fun `getUser returns Error when response is not successful` () = runTest {
        val userId = "octocat"
        coEvery { api.getUser(userId) } returns Response.error(404, "Not Found".toResponseBody(null))

        val result = repository.getUser(userId)

        assert(result is Result.Error)
        assertEquals("User not found", (result as Result.Error).message)
    }

    @Test
    fun `getUser returns Error when user id is null`() = runTest{
        val userId = "octocat"
        val userResponse = UserResponse(null, "https://avatars.githubusercontent.com/u/583231?v=4")

        coEvery { api.getUser(userId) } returns Response.success(userResponse)

        val result = repository.getUser(userId)

        assert(result is Result.Error)
        assertEquals("Invalid user response", (result as Result.Error).message)

    }

    @Test
    fun `getUser returns Error when Network is not available`() = runTest {
        val userId = "octocat"
        coEvery { api.getUser(userId) } throws Exception("No internet connection")

        val result = repository.getUser(userId)

        assert(result is Result.Error)
        assertEquals("Something went wrong: No internet connection", (result as Result.Error).message)

    }

    @Test
    fun `getUserRepositories returns list of repositories when response is successful`() = runTest {
        val userId = "octocat"
        val repositoryResponse = listOf(
            RepositoryResponse("repo1", "desc1", "2025-09-20T00:00:00Z", 100, 100),
            RepositoryResponse("repo2", "desc2", "2025-09-20T00:00:00Z", 200, 200)
        )
        coEvery { api.getUserRepositories(userId) } returns Response.success(repositoryResponse)

        val result = repository.getUserRepositories(userId)

        assert(result is Result.Success)
        assertEquals(repositoryResponse.map { repo ->
            Repository(
                repo.name ?: "",
                repo.description,
                repo.updatedAt ?: "",
                repo.stargazersCount ?: 0,
                repo.forksCount ?: 0
            )
        }, (result as Result.Success).data)

    }

    @Test
    fun `getUserRepositories returns Error when response is not successful`() =runTest {
        val userId = "octocat"
        coEvery { api.getUserRepositories(userId) } returns Response.error(404, "Not Found".toResponseBody(null))

        val result = repository.getUserRepositories(userId)

        assert(result is Result.Error)
        assertEquals("Failed to fetch repositories: 404", (result as Result.Error).message)

    }


    @Test
    fun `getUserRepositories returns Error when Network is not available`() = runTest {
        val userId = "octocat"
        coEvery { api.getUserRepositories(userId) } throws Exception("No internet connection")

        val result = repository.getUserRepositories(userId)

        assert(result is Result.Error)
        assertEquals("Something went wrong: No internet connection", (result as Result.Error).message)
    }
}