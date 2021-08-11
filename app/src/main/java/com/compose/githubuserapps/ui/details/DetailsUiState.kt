package com.compose.githubuserapps.ui.details

import com.compose.githubuserapps.data.GithubUser

sealed class DetailsUiState{
    object Loading : DetailsUiState()
    data class Error(val message: String?) : DetailsUiState()
    data class Success(val details: GithubUser): DetailsUiState()
}
