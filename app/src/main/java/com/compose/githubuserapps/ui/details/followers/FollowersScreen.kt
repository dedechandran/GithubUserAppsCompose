package com.compose.githubuserapps.ui.details.followers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.githubuserapps.data.GithubUser
import com.compose.githubuserapps.ui.component.GithubUserItem

@ExperimentalMaterialApi
@Composable
fun FollowersScreen(vm: FollowersViewModel = hiltViewModel()) {
    LazyColumn{
        item { GithubUserItem(item = GithubUser("name","1234")){} }
        item { GithubUserItem(item = GithubUser("name","1234")){} }
    }
}