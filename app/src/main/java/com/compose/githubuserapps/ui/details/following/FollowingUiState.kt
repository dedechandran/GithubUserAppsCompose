package com.compose.githubuserapps.ui.details.following

import com.compose.githubuserapps.data.GithubUser

sealed class FollowingUiState{
    object Loading : FollowingUiState()
    data class Error(val message: String?) : FollowingUiState()
    data class Success(val list: List<GithubUser>): FollowingUiState()
}
