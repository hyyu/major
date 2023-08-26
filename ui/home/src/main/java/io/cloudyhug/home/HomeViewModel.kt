package io.cloudyhug.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.cloudyhug.home.event.HomeEvent
import io.cloudyhug.home.interactor.HomeInteractor
import io.cloudyhug.home.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetUser -> getUser()
        }
    }

    private fun getUser() {
        _uiState.value = _uiState.value.build {
            isLoading = true
        }

        viewModelScope.launch {
            Log.v("getUser", "Launching /user from viewModel")

            val result = interactor.getUser()

            result.exceptionOrNull()?.let {
                Log.e("getUser", "Error: message = $it")
                _uiState.value = _uiState.value.build {
                    isLoading = false
                }
            } ?: run {
                Log.v("getUser", "Request success")
                Log.v("getUser", "Result: $result")
                result.getOrNull()?.let { user ->
                    _uiState.value = _uiState.value.build {
                        this.user = user
                    }
                } ?: {
                    _uiState.value = _uiState.value.build {
                        isLoading = false
                    }
                }
            }

        }
    }

}
