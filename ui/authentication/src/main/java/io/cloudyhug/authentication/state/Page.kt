package io.cloudyhug.authentication.state

sealed class Page {
    object Login : Page()
    object Register : Page()
}
