package io.cloudyhug.major.ui.compose.preview.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.cloudyhug.authentication.state.Page
import io.cloudyhug.authentication.compose.AuthenticationContent
import io.cloudyhug.authentication.state.AuthenticationState
import io.cloudyhug.major.ui.theme.MajorTheme

@Preview(name = "Light theme login", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark theme login", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAuthentication() {
    MajorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AuthenticationContent(
                page = Page.Login,
                login = "",
                password = "",
                uiState = AuthenticationState(
                    isLoading = false,
                    isAuthenticated = false
                )
            )
        }
    }
}
