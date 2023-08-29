package io.cloudyhug.navigation.navigator

data class AuthNavigator(
    val navigateUp: () -> Unit,
    val navigateToRegister: () -> Unit,
    val onUserAuthenticated: () -> Unit,
)
