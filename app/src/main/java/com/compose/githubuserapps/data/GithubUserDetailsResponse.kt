package com.compose.githubuserapps.data

import com.google.gson.annotations.SerializedName

data class GithubUserDetailsResponse(
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("created_at") val createdDateTime: String?
)
