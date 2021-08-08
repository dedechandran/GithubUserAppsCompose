package com.compose.githubuserapps.data

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubUserService {
    @GET("users")
    suspend fun searchGithubUser(@Query("q") keyword: String?): GithubUserResponse
}