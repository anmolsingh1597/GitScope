package com.example.gitscope.domain.usecase

import javax.inject.Inject
import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.repository.GitHubScopeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.example.gitscope.data.remote.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserRepositoryUseCase @Inject constructor(
    private val repository: GitHubScopeRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(userId: String): Result<List<Repository>> {
        return if (userId.isBlank()) {
            Result.Error("User ID cannot be empty")
        } else {
            repository.getUserRepositories(userId.trim())
        }
    }
    //TODO: can be used if use case defined
/*    operator fun invoke(userId: String): Flow<Result<List<Repository>>> = flow {
        if (userId.isBlank()) {
            emit(Result.Error("User ID cannot be empty"))
        } else {
            emit(Result.Loading())
            emit(repository.getUserRepositories(userId.trim()))
        }
    }.flowOn(ioDispatcher)*/
}