package com.example.gitscope.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String?
)

data class RepositoryResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("forks") val forksCount: Int?

)