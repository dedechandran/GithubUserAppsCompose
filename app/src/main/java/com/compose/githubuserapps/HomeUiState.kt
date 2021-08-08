package com.compose.githubuserapps

import com.compose.githubuserapps.data.GithubUser

sealed class HomeUiState{
    object Loading : HomeUiState()
    data class Error(val message: String?) : HomeUiState()
    data class Success(val list: List<GithubUser>): HomeUiState()
}
