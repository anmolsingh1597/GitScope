package com.example.gitscope.domain.usecase

import com.example.gitscope.data.di.modules.DefaultDispatcher
import com.example.gitscope.domain.model.UserWithRepositories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import com.example.gitscope.data.remote.Result
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class GetUserWithRepositoriesUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoryUseCase,
    private val calculateTotalForkUseCase: CalculateTotalForkUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(userId: String): Result<UserWithRepositories> = withContext(defaultDispatcher) {
        if (userId.isBlank()) {
            return@withContext Result.Error("User ID cannot be empty")
        }

        try {
            val userDeferred = async { getUserUseCase(userId) }
            val reposDeferred = async { getUserRepositoriesUseCase(userId) }

            val userResult = userDeferred.await()
            val reposResult = reposDeferred.await()

            when {
                userResult is Result.Error -> Result.Error<UserWithRepositories>(userResult.message)
                reposResult is Result.Error -> Result.Error<UserWithRepositories>(reposResult.message)
                userResult is Result.Success && reposResult is Result.Success -> {
                    val totalForks = calculateTotalForkUseCase(reposResult.data)
                    Result.Success(
                        UserWithRepositories(
                            user = userResult.data,
                            repositories = reposResult.data,
                            totalForks = totalForks
                        )
                    )
                }
                else -> Result.Error("Unexpected error occurred")
            }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> throw e
                is UnknownHostException -> Result.Error("No internet connection")
                else -> Result.Error(e.message ?: "Operation failed")
            }
        }
    }

    //TODO: Using reactive programming for regular api call, might be overkill
    /*operator fun invoke(userId: String): Flow<Result<UserWithRepositories>> {
        if (userId.isBlank()) {
            return flowOf(Result.Error("User ID cannot be empty"))
        }

        return combine(
            getUserUseCase(userId),
            getUserRepositoriesUseCase(userId)
        ) { userResult, repositoriesResult ->
            when {
                userResult is Result.Loading || repositoriesResult is Result.Loading -> {
                    Result.Loading<UserWithRepositories>()
                }
                userResult is Result.Error -> Result.Error<UserWithRepositories>(userResult.message)
                repositoriesResult is Result.Error -> Result.Error<UserWithRepositories>(repositoriesResult.message)
                userResult is Result.Success && repositoriesResult is Result.Success -> {
                    val user = userResult.data
                    val repositories = repositoriesResult.data
                    val totalForks = calculateTotalForkUseCase(repositories)
                    Result.Success(
                        UserWithRepositories(
                            user = user,
                            repositories = repositories,
                            totalForks = totalForks
                        )
                    )
                }
                else -> Result.Error<UserWithRepositories>("Something went wrong")
            }
        }.catch { e ->
            when (e) {
                is CancellationException -> throw e
                is UnknownHostException -> emit(Result.Error("No internet connection"))
                else -> emit(Result.Error("Something went wrong: ${e.message}"))
            }
        }.flowOn(defaultDispatcher)
    }*/
}