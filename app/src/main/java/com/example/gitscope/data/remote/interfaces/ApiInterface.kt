package com.example.gitscope.data.remote.interfaces

import com.example.gitscope.data.model.RepositoryResponse
import com.example.gitscope.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<UserResponse>

    @GET("users/{userId}/repos")
    suspend fun getUserRepositories(@Path("userId") userId: String): Response<List<RepositoryResponse>>
}