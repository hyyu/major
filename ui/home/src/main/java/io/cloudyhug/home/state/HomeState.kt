package io.cloudyhug.home.state

import io.cloudyhug.domain.usecase.user.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null
) {
    fun build(block: Builder.() -> Unit) = Builder(HomeState()).apply(block).build()

    class Builder(uiModel: HomeState) {
        var isLoading = uiModel.isLoading
        var user = uiModel.user

        fun build(): HomeState {
            return HomeState(
                isLoading = isLoading,
                user = user
            )
        }
    }
}
