package io.cloudyhug.home.event

sealed class HomeEvent {
    object GetUser : HomeEvent()
}
