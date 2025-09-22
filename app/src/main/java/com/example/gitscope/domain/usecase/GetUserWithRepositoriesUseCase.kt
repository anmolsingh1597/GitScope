package com.example.gitscope.domain.usecase

import com.example.gitscope.di.modules.DefaultDispatcher
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
}