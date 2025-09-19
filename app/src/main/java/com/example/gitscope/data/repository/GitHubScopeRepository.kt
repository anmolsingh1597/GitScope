package com.example.gitscope.data.repository

import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User
import kotlinx.coroutines.flow.Flow
import com.example.gitscope.data.remote.Result

interface GitHubScopeRepository {
    suspend fun getUser(userId: String): Result<User>
    suspend fun getUserRepositories(userId: String): Result<List<Repository>>

    fun getUserFlow(userId: String): Flow<Result<User>>
    fun getUserRepositoriesFlow(userId: String): Flow<Result<List<Repository>>>


}

