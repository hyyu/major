package io.cloudyhug.authentication.state

data class AuthenticationState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false
)  {

    fun build(block: Builder.() -> Unit) = Builder(AuthenticationState()).apply(block).build()

    class Builder(uiModel: AuthenticationState) {
        var isLoading = uiModel.isLoading
        var isAuthenticated = uiModel.isAuthenticated

        fun build(): AuthenticationState {
            return AuthenticationState(
                isLoading = isLoading,
                isAuthenticated = isAuthenticated
            )
        }
    }
}
