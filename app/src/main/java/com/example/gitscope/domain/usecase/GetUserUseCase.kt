package com.example.gitscope.domain.usecase

import com.example.gitscope.data.di.modules.IoDispatcher
import com.example.gitscope.data.model.User
import com.example.gitscope.data.repository.GitHubScopeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import com.example.gitscope.data.remote.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserUseCase @Inject constructor(
    private val repository: GitHubScopeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(userId: String): Result<User> {
        return if (userId.isBlank()) {
            Result.Error("User ID cannot be empty")
        } else {
            repository.getUser(userId.trim())
        }
    }

    //TODO: Can be used in future with proper use case defined
/*    operator fun invoke(userId: String): Flow<Result<User>> = flow {
        if (userId.isBlank()) {
            emit(Result.Error("User ID cannot be empty"))
        } else {
            emit(Result.Loading())
            emit(repository.getUser(userId.trim()))
        }
    }.flowOn(ioDispatcher)*/
}