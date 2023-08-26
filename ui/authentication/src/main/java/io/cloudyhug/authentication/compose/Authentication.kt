package io.cloudyhug.authentication.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.cloudyhug.authentication.AuthViewModel
import io.cloudyhug.authentication.event.AuthenticationEvent
import io.cloudyhug.authentication.state.Page
import io.cloudyhug.common.compose.frame.Frame
import io.cloudyhug.navigation.Screen

@Composable
fun Authentication(
    viewModel: AuthViewModel = hiltViewModel(),
    page: Page,
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    onRegisterPageRequested: () -> Unit = {},
    onUserAuthenticated: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = state.isAuthenticated) {
        if (state.isAuthenticated)
            onUserAuthenticated()
    }

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val onSendClickedAuthenticate = {
        viewModel.handleEvent(
            AuthenticationEvent.Authenticate(
                login = login,
                password = password
            )
        )
    }
    val onSendClickedRegister = {
        viewModel.handleEvent(
            AuthenticationEvent.Register(
                login = login,
                password = password,
                signInOnSuccess = true
            )
        )
    }

    Frame(
        modifier = Modifier
            .fillMaxSize(),
        currentScreen = currentScreen,
        canNavigateBack = canNavigateBack,
        navigateUp = navigateUp,
        snackbarState = viewModel.snackbarState,
        onShowSnackbar = { viewModel.snackbarDisplayed() }
    ) { paddingValues ->
        AuthenticationContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            page = page,
            login = login,
            password = password,
            uiState = state,
            onLoginChanged = { login = it },
            onPasswordChanged = { password = it },
            onSendButtonClicked = if (page == Page.Login) onSendClickedAuthenticate else onSendClickedRegister,
            onRegisterPageRequested = onRegisterPageRequested
        )
    }
}
