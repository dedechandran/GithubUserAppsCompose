package com.compose.githubuserapps.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.compose.githubuserapps.R
import com.compose.githubuserapps.data.GithubUser
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vm: DetailsViewModel = hiltViewModel(),
    userName: String
) {
    val userDetails = vm.detailsUiState.value
    LaunchedEffect(key1 = true) {
        vm.getDetails(userName = userName)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (userDetails) {
            is DetailsUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = MaterialTheme.colors.primary)
                ) {
                    DetailsAppBar(navController = navController)
                    DetailsAppBarContent(userDetails = userDetails.details)
                }
                DetailsBody(userName = userName)
            }
            is DetailsUiState.Loading -> {

            }
            is DetailsUiState.Error -> {

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DetailsAppBar(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = dimensionResource(id = R.dimen.spacing_4),
                end = dimensionResource(id = R.dimen.spacing_4),
                top = dimensionResource(id = R.dimen.spacing_4)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape),
            onClick = {
                navController.popBackStack()
            },
            indication = rememberRipple(color = Color.White),
            color = Color.Transparent
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                tint = Color.White
            )
        }
        Surface(
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape),
            color = Color.Transparent
        ) {
            Text(
                text = "User Detail",
                style = MaterialTheme.typography.subtitle1,
                color = Color.White
            )
        }
        Surface(
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape),
            onClick = {},
            indication = rememberRipple(color = Color.White),
            color = Color.Transparent
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Arrow Back",
                tint = Color.White
            )
        }
    }
}

@Composable
fun DetailsAppBarContent(modifier: Modifier = Modifier, userDetails: GithubUser) {
    // Github User Details
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_4)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = userDetails.userAvatar,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = modifier.size(72.dp)
            )
        }
        Text(
            text = userDetails.userName,
            style = MaterialTheme.typography.subtitle1,
            modifier = modifier.padding(
                top = dimensionResource(
                    id = R.dimen.spacing_2
                )
            )
        )
        Text(
            text = "Location",
            style = MaterialTheme.typography.subtitle2,
            modifier = modifier.padding(
                top = dimensionResource(
                    id = R.dimen.spacing_1
                )
            )
        )
        Text(
            text = "Since : 2020", style = MaterialTheme.typography.caption,
            modifier = modifier.padding(
                top = dimensionResource(
                    id = R.dimen.spacing_1
                )
            )
        )
    }
}


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun DetailsBody(
    modifier: Modifier = Modifier,
    tabs: List<TabItem> = listOf(TabItem.Followers, TabItem.Following),
    userName: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val pagerState = rememberPagerState(pageCount = tabs.size)
        val scope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = tabPositions
                    )
                )
            }) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    text = {
                        Text(text = tabItem.title)
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            tabs[page].screen.invoke(userName)
        }
    }
}


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DetailsHeaderPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.primary)
    ) {
        DetailsAppBar(navController = rememberNavController())
        DetailsAppBarContent(
            userDetails = GithubUser(
                userName = "xxx",
                userAvatar = "xxx"
            )
        )
    }
}