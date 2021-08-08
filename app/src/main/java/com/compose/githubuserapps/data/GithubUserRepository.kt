package com.compose.githubuserapps.data

import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    suspend fun searchGithubUser(keyword: String?): Flow<List<GithubUser>>
}