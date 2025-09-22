package com.example.gitscope.domain.usecase

import com.example.gitscope.di.modules.IoDispatcher
import javax.inject.Inject
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.repository.GitHubScopeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.example.gitscope.data.remote.Result

class GetUserRepositoryUseCase @Inject constructor(
    private val repository: GitHubScopeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(userId: String): Result<List<Repository>> {
        return if (userId.isBlank()) {
            Result.Error("User ID cannot be empty")
        } else {
            repository.getUserRepositories(userId.trim())
        }
    }
}