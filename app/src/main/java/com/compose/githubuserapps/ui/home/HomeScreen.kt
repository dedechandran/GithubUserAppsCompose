package com.compose.githubuserapps.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.compose.githubuserapps.R
import com.compose.githubuserapps.ui.component.GithubUserItem

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(vm: HomeViewModel = hiltViewModel(), navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { GithubUserTopAppBar() },
    ) {
        GithubUserHome(homeViewModel = vm, navController = navController)
    }
}


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun GithubUserHome(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(dimensionResource(id = R.dimen.spacing_4))
    ) {
        val searchKeyword = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        GithubUserSearchField(
            searchKeyword = searchKeyword.value,
            onSearchKeywordChange = {
                searchKeyword.value = it
            },
            keyboardController = keyboardController,
            vm = homeViewModel
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_2)))
        GithubUserList(homeViewModel, navController = navController)
    }
}

@ExperimentalComposeUiApi
@Composable
fun GithubUserSearchField(
    modifier: Modifier = Modifier,
    searchKeyword: String,
    onSearchKeywordChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    vm: HomeViewModel
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = searchKeyword,
        onValueChange = {
            onSearchKeywordChange.invoke(it)
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            vm.searchGithubUser(searchKeyword)
        }),
        singleLine = true
    )
}

@Composable
fun GithubUserTopAppBar() {
    TopAppBar(
        title = {
            Text(text = "Github User Apps")
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }

        }
    )
}


@ExperimentalMaterialApi
@Composable
fun GithubUserList(
    vm: HomeViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    when (val uiState = vm.homeUiState.value) {
        is HomeUiState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }
        }
        is HomeUiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    "Something went error, please try again",
                    textAlign = TextAlign.Center,
                    modifier = modifier.align(Alignment.Center)
                )
            }
        }
        is HomeUiState.Success -> {
            if (uiState.list.isEmpty()) {
                NoResultFound()
            } else {
                LazyColumn {
                    items(uiState.list) {
                        GithubUserItem(item = it){
                            navController.navigate("details/$it")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun NoResultFound(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_result_found))
    LottieAnimation(
        composition,
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    )
}


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Scaffold(
        topBar = { GithubUserTopAppBar() }
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun GithubUserListPreview() {
//        GithubUserList()
}

@Preview(showBackground = true)
@Composable
fun GithubUserItemPreview() {
//        GithubUserItem()
}