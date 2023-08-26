package io.cloudyhug.common.compose.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.cloudyhug.ui.common.R.drawable
import io.cloudyhug.ui.common.R.string

@Composable
fun CredentialsFormBlock(
    loginLabel: String,
    login: String,
    passwordLabel: String,
    password: String,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.65f),
        value = login,
        onValueChange = onLoginChanged,
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = loginLabel,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(0.65f),
        value = password,
        onValueChange = onPasswordChanged,
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(
                text = passwordLabel,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(id = if (passwordVisibility) drawable.ic_visibility_on else drawable.ic_visibility_off),
                    contentDescription = stringResource(id = string.icon_visibility_content_description)
                )
            }
        }
    )
}
