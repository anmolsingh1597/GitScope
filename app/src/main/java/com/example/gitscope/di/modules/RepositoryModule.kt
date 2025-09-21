package com.example.gitscope.di.modules

import com.example.gitscope.data.repository.GitHubScopeRepository
import com.example.gitscope.data.repository.GitHubScopeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGitHubRepository(
        gitHubRepositoryImpl: GitHubScopeRepositoryImpl
    ): GitHubScopeRepository
}