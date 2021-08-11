package com.compose.githubuserapps.ui.details.following

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
class FollowingViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {

    private val _followingUiState = mutableStateOf<FollowingUiState>(FollowingUiState.Loading)
    val followingUiState: State<FollowingUiState>
        get() = _followingUiState

    fun getUserFollowing(userName: String){
        viewModelScope.launch {
            githubUserRepository.getGithubFollowing(userName = userName)
                .catch {
                    _followingUiState.value = FollowingUiState.Error(message = it.message)
                }
                .collect {
                    _followingUiState.value = FollowingUiState.Success(list = it)
                }
        }
    }

}