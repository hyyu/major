package io.cloudyhug.authentication

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.cloudyhug.authentication.event.AuthenticationEvent
import io.cloudyhug.authentication.interactor.AuthInteractor
import io.cloudyhug.authentication.state.AuthenticationState
import io.cloudyhug.common.viewmodel.BaseViewModel
import io.cloudyhug.domain.model.error.ErrorResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthInteractor,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationState())
    val uiState: StateFlow<AuthenticationState> = _uiState

    fun handleEvent(event: AuthenticationEvent) {
        when (event) {
            is AuthenticationEvent.Register -> registerAccount(
                event.login,
                event.password,
                event.signInOnSuccess
            )

            is AuthenticationEvent.Authenticate -> authenticateUser(event.login, event.password)
        }
    }

    private fun registerAccount(
        login: String,
        password: String,
        signInOnSuccess: Boolean = false,
    ) {
        updateUi {
            isLoading = true
        }

        viewModelScope.launch {
            Log.v("registerAccount", "Launching /register from viewModel")

            val result = interactor.registerUser(login, password)

            result.exceptionOrNull()?.let { cause ->
                consumeError(cause)
                updateUi {
                    isLoading = false
                }
            } ?: run {
                Log.v("registerAccount", "Request success")
                Log.v("registerAccount", "Result: $result")
                if (signInOnSuccess) {
                    Log.v("registerAccount", "Processing to authentication")
                    authenticateUser(login, password)
                }
            }
        }
    }

    private fun authenticateUser(login: String, password: String) {
        updateUi {
            isLoading = true
        }

        viewModelScope.launch {
            Log.v("Authenticate", "Launching /connect request from viewModel")

            val result = interactor.authenticateUser(login, password)

            result.exceptionOrNull()?.let { cause ->
                consumeError(cause)
                updateUi {
                    isLoading = false
                }
            } ?: run {
                Log.v("Authenticate", "Request success")
                Log.v("Authenticate", "Result: $result")

                updateUi {
                    isLoading = false
                    isAuthenticated = true
                }
            }
        }
    }

    private fun consumeError(cause: Throwable) {
        Log.e("Authenticate", "Request failure")
        when (cause) {
            is ErrorResponseException -> {
                Log.e("Authenticate", "Error: ${cause.error}")
                showSnackBar(
                    message = cause.error.message
                )
            }
            is IOException -> {
                Log.e("Authenticate", "Error: $cause")
                showSnackBar(
                    message = "Could not reach the server, please verify your internet connection"
                )
            }
            else -> {
                Log.e("Authenticate", "Error: $cause")
                showSnackBar(
                    message = cause.message ?: "Unknown error, please try again later"
                )
            }
        }
    }

    private fun updateUi(block: AuthenticationState.Builder.() -> Unit) {
        _uiState.value = _uiState.value.build(block)
    }

}
