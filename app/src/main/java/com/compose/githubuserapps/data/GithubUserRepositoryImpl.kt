package com.compose.githubuserapps.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val githubUserService: GithubUserService
) : GithubUserRepository {
    override suspend fun searchGithubUser(keyword: String?): Flow<List<GithubUser>> {
        return flow {
            val result = githubUserService.searchGithubUser(keyword = keyword).items?.map {
                GithubUser(
                    userName = it.login ?: "-",
                    userAvatar = it.avatarUrl ?: "-"
                )
            }.orEmpty()
            emit(result)
        }
    }

    override suspend fun getGithubFollowers(userName: String): Flow<List<GithubUser>> {
        return flow {  }
    }

    override suspend fun getGithubFollowing(userName: String): Flow<List<GithubUser>> {
        return flow {
            val result = githubUserService.getGithubUserFollowing(userName = userName)?.map {
                GithubUser(
                    userName = it.login ?: "-",
                    userAvatar = it.avatarUrl ?: "-"
                )
            }.orEmpty()
            emit(result)
        }
    }

    override suspend fun getGithubUserDetails(userName: String): Flow<GithubUser> {
        return flow {
            val result = githubUserService.getGithubUserDetails(userName = userName).run {
                GithubUser(
                    userName = userName,
                    userAvatar = avatarUrl ?: "-"
                )
            }
            emit(result)
        }
    }
}