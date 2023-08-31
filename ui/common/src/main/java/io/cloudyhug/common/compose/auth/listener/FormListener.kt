package io.cloudyhug.common.compose.auth.listener

data class FormListener(
    val onLoginChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
)
