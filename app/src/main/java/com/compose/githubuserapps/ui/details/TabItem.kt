package com.compose.githubuserapps.ui.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.compose.githubuserapps.ui.details.followers.FollowersScreen
import com.compose.githubuserapps.ui.details.following.FollowingScreen

sealed class TabItem(val title: String, val screen: @Composable (String) -> Unit) {
    @ExperimentalMaterialApi
    object Followers : TabItem("Followers", { userName -> FollowersScreen() })
    @ExperimentalMaterialApi
    object Following : TabItem("Following", { userName -> FollowingScreen(userName = userName) })
}
