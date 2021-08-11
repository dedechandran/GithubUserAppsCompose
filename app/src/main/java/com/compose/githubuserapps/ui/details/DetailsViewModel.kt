package com.compose.githubuserapps.ui.details

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
class DetailsViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : ViewModel(){

    private val _detailsUiState = mutableStateOf<DetailsUiState>(DetailsUiState.Loading)
    val detailsUiState: State<DetailsUiState>
        get() = _detailsUiState

    fun getDetails(userName: String){
        viewModelScope.launch {
            githubUserRepository.getGithubUserDetails(userName = userName)
                .catch {
                    _detailsUiState.value = DetailsUiState.Error(message = it.message)
                }
                .collect {
                    _detailsUiState.value = DetailsUiState.Success(details = it)
                }
        }
    }

}