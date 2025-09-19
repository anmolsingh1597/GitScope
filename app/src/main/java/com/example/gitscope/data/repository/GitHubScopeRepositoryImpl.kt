package com.example.gitscope.data.repository

import com.example.gitscope.data.di.modules.IoDispatcher
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User
import com.example.gitscope.data.remote.interfaces.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import com.example.gitscope.data.remote.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException

@Singleton
class GitHubScopeRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GitHubScopeRepository {
    override suspend fun getUser(userId: String): Result<User> = withContext(ioDispatcher) {
        try {
            val response = apiInterface.getUser(userId)
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse?.name != null && userResponse?.avatarUrl != null) {
                    Result.Success(
                        User(
                            name = userResponse.name,
                            avatarUrl = userResponse.avatarUrl
                        )
                    )
                } else {
                    Result.Error(
                        "Invalid user response"
                    )
                }
            } else {
                when (response.code()) {
                    404 -> Result.Error("User not found")
                    403 -> Result.Error("API rate limit exceeded")
                    else -> Result.Error("Something went wrong: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw e
                is UnknownHostException -> Result.Error("No internet connection")
                else -> Result.Error("Something went wrong: ${e.message}")
            }
        }
    }


    override suspend fun getUserRepositories(userId: String): Result<List<Repository>> =
        withContext(ioDispatcher) {
            try {
                val response = apiInterface.getUserRepositories(userId)
                if (response.isSuccessful) {
                    val repositoriesResponse = response.body() ?: emptyList()
                    val repositories = repositoriesResponse.mapNotNull { repo ->
                        if (repo.name != null && repo.updatedAt != null &&
                            repo.stargazersCount != null && repo.forksCount != null
                        ) {
                            Repository(
                                name = repo.name,
                                description = repo.description,
                                updatedAt = repo.updatedAt,
                                stargazersCount = repo.stargazersCount,
                                forksCount = repo.forksCount
                            )
                        } else null
                    }
                    Result.Success(repositories)
                } else {
                    Result.Error("Failed to fetch repositories: ${response.code()}")
                }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> throw e
                    is UnknownHostException -> Result.Error("No internet connection")
                    else -> Result.Error("Something went wrong: ${e.message}")
                }
            }
        }

    override fun getUserFlow(userId: String): Flow<Result<User>> =
        flow {
            emit(Result.Loading())
            emit(getUser(userId))
        }.flowOn(ioDispatcher)

    override fun getUserRepositoriesFlow(userId: String): Flow<Result<List<Repository>>> = flow {
        emit(Result.Loading())
        emit(getUserRepositories(userId))
    }.flowOn(ioDispatcher)
}