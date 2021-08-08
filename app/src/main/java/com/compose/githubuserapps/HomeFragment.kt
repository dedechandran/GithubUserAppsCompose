package com.compose.githubuserapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.compose.githubuserapps.data.GithubUser
import com.compose.githubuserapps.ui.theme.GithubUserAppsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val vm: HomeViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                GithubUserAppsTheme {
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = { GithubUserTopAppBar() },
                    ) {
                        GithubUserHome(homeViewModel = vm)
                    }
                }
            }
        }
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
    @ExperimentalComposeUiApi
    @Composable
    fun GithubUserHome(
        modifier: Modifier = Modifier,
        homeViewModel: HomeViewModel
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
            GithubUserList(homeViewModel)
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




    @ExperimentalMaterialApi
    @Composable
    fun GithubUserList(
        vm: HomeViewModel,
        modifier: Modifier = Modifier
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
                            GithubUserItem(item = it)
                        }
                    }
                }
            }
        }

    }

    @ExperimentalMaterialApi
    @Composable
    fun GithubUserItem(
        modifier: Modifier = Modifier,
        item: GithubUser
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = dimensionResource(id = R.dimen.spacing_1)),
            onClick = {

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
}