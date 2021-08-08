package com.compose.githubuserapps

import androidx.compose.runtime.Composable

sealed class TabItem(val title: String, val screen : @Composable () -> Unit){
    object Followers : TabItem("Followers", {})
    object Following : TabItem("Following", {})
}
