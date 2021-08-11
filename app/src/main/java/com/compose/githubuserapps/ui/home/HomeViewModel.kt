package com.compose.githubuserapps.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.githubuserapps.data.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {

    private val _homeUiState = mutableStateOf<HomeUiState>(HomeUiState.Success(emptyList()))
    val homeUiState: State<HomeUiState>
        get() = _homeUiState

    fun searchGithubUser(keyword: String?){
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Loading
            githubUserRepository.searchGithubUser(keyword = keyword)
                .catch { it ->
                    _homeUiState.value = HomeUiState.Error(it.message)
                }
                .collect {
                    _homeUiState.value = HomeUiState.Success(it)
                }
        }
    }


}