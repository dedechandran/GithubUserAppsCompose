package com.compose.githubuserapps.ui.details.followers

import com.compose.githubuserapps.data.GithubUser

sealed class FollowersUiState{
    object Loading : FollowersUiState()
    data class Error(val message: String?) : FollowersUiState()
    data class Success(val list: List<GithubUser>): FollowersUiState()
}
