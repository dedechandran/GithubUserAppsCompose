package com.compose.githubuserapps.di

import com.compose.githubuserapps.data.GithubUserRepository
import com.compose.githubuserapps.data.GithubUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideGithubUserRepository(githubUserRepositoryImpl: GithubUserRepositoryImpl): GithubUserRepository {
        return githubUserRepositoryImpl
    }

}