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
import io.cloudyhug.authentication.AuthViewModel
import io.cloudyhug.authentication.event.AuthenticationEvent
import io.cloudyhug.authentication.state.Page
import io.cloudyhug.common.compose.auth.listener.FormListener
import io.cloudyhug.common.frame.compose.Frame
import io.cloudyhug.common.frame.model.AppBarData
import io.cloudyhug.navigation.navigator.AuthNavigator

@Composable
fun Authentication(
    viewModel: AuthViewModel,
    navigator: AuthNavigator,
    appBarData: AppBarData,
    page: Page,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = state.isAuthenticated) {
        if (state.isAuthenticated)
            navigator.onUserAuthenticated()
    }

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val formListener = FormListener(
        onLoginChanged = { login = it },
        onPasswordChanged = { password = it }
    )

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
        appBarData = appBarData,
        navigateUp = navigator.navigateUp,
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
            formListener = formListener,
            uiState = state,
            onSendButtonClicked = if (page == Page.Login) onSendClickedAuthenticate else onSendClickedRegister,
            onRegisterPageRequested = navigator.navigateToRegister
        )
    }
}
