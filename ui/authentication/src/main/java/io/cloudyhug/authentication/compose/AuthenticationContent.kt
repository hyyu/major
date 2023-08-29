package io.cloudyhug.authentication.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.cloudyhug.authentication.state.AuthenticationState
import io.cloudyhug.authentication.state.Page
import io.cloudyhug.common.compose.auth.CredentialsFormBlock
import io.cloudyhug.common.compose.auth.RegisterBlock
import io.cloudyhug.common.compose.auth.listener.FormListener
import io.cloudyhug.common.compose.button.ButtonWithLoader
import io.cloudyhug.common.compose.display.TitleBlock
import io.cloudyhug.ui.authentication.R.drawable
import io.cloudyhug.ui.authentication.R.string

@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    formListener: FormListener,
    page: Page,
    login: String = "",
    password: String = "",
    uiState: AuthenticationState,
    onSendButtonClicked: () -> Unit = {},
    onRegisterPageRequested: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBlock(
            logo = painterResource(id = drawable.ic_logo),
            logoContentDescription = stringResource(id = string.icon_cake_content_description),
            title = stringResource(id = string.app_name)
        )

        CredentialsFormBlock(
            login = login,
            loginLabel = stringResource(string.label_login),
            password = password,
            passwordLabel = stringResource(string.label_password),
            formListener = formListener
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonWithLoader(
            modifier = Modifier
                .fillMaxWidth(0.35f),
            title = stringResource(
                id = if (page == Page.Login) string.label_sign_in else string.label_register
            ).uppercase(),
            isLoading = uiState.isLoading,
            onClickAction = onSendButtonClicked
        )

        if (page == Page.Login) {
            Spacer(modifier = Modifier.height(8.dp))
            RegisterBlock(onClick = onRegisterPageRequested)
        }

    }
}
