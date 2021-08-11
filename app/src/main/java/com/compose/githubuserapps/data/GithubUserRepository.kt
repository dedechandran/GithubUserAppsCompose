package com.compose.githubuserapps.data

import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    suspend fun searchGithubUser(keyword: String?): Flow<List<GithubUser>>
    suspend fun getGithubFollowers(userName: String): Flow<List<GithubUser>>
    suspend fun getGithubFollowing(userName: String): Flow<List<GithubUser>>
    suspend fun getGithubUserDetails(userName: String): Flow<GithubUser>
}