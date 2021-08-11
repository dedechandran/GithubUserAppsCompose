package com.compose.githubuserapps.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserService {
    @GET("search/users")
    suspend fun searchGithubUser(@Query("q") keyword: String?): GithubUserResponse

    @GET("users/{userName}")
    suspend fun getGithubUserDetails(@Path("userName") userName: String): GithubUserDetailsResponse

    @GET("users/{userName}/following")
    suspend fun getGithubUserFollowing(@Path("userName") userName: String): List<GithubUserFollowingResponse>?
}