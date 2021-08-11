package com.compose.githubuserapps.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.compose.githubuserapps.data.GithubUser
import com.compose.githubuserapps.R

@ExperimentalMaterialApi
@Composable
fun GithubUserItem(
    modifier: Modifier = Modifier,
    item: GithubUser,
    onItemClicked: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_1)),
        onClick = {
            onItemClicked.invoke(item.userName)
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_2)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = item.userAvatar,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = modifier.size(48.dp)
            )
            Text(
                text = item.userName,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.spacing_4)),
                style = MaterialTheme.typography.h6
            )
        }

    }
}