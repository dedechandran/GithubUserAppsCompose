package com.compose.githubuserapps.data

import com.google.gson.annotations.SerializedName

data class GithubUserFollowingResponse(
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
)
