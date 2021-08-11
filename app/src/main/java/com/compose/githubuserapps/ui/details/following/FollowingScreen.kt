package com.compose.githubuserapps.ui.details.following

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.githubuserapps.data.GithubUser
import com.compose.githubuserapps.ui.component.GithubUserItem

@ExperimentalMaterialApi
@Composable
fun FollowingScreen(vm: FollowingViewModel = hiltViewModel(), userName: String) {
    val followingUiState = vm.followingUiState.value
    LaunchedEffect(key1 = true) {
        vm.getUserFollowing(userName = userName)
    }

    when (followingUiState) {
        is FollowingUiState.Loading -> {

        }
        is FollowingUiState.Success -> {
            LazyColumn {
                items(followingUiState.list) {
                    GithubUserItem(item = it, onItemClicked = {})
                }
            }
        }
        is FollowingUiState.Error -> {

        }
    }


}