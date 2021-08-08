package com.compose.githubuserapps.data

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("items") val items: List<Item>?
){
    data class Item(
        @SerializedName("login") val login: String?,
        @SerializedName("avatar_url") val avatarUrl: String?
    )
}
