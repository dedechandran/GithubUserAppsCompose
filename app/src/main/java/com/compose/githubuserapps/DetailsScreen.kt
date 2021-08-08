package com.compose.githubuserapps

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        DetailsHeader()
        DetailsBody()
    }
}


@ExperimentalMaterialApi
@Composable
fun DetailsHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(id = R.dimen.spacing_4)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = modifier
                        .wrapContentSize()
                        .clip(CircleShape),
                    onClick = {},
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
                        style = MaterialTheme.typography.h6,
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
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = ""
                    )
                }
                Text(text = "Username", style = MaterialTheme.typography.subtitle1)
                Text(text = "Location", style = MaterialTheme.typography.subtitle2)
                Text(text = "Since : 2020", style = MaterialTheme.typography.caption)
            }
        }
    }
}


@ExperimentalPagerApi
@Composable
fun DetailsBody(
    modifier: Modifier = Modifier,
    tabs: List<TabItem> = listOf(TabItem.Followers, TabItem.Following)
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
        HorizontalPager(state = pagerState) {
            tabs.forEachIndexed { index, tabItem ->
                tabs[index].screen
            }
        }
    }
}


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DetailsHeaderPreview() {
    DetailsScreen()
}