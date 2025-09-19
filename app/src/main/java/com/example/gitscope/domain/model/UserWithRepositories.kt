package com.example.gitscope.domain.model

import com.example.gitscope.data.model.Repository
import com.example.gitscope.data.model.User

data class UserWithRepositories(
    val user: User,
    val repositories: List<Repository>,
    val totalForks: Int
)