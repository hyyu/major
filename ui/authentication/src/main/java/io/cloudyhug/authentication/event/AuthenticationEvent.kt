package io.cloudyhug.authentication.event

sealed class AuthenticationEvent {
    data class Register(val login: String, val password: String, val signInOnSuccess: Boolean = false): AuthenticationEvent()
    data class Authenticate(val login: String, val password: String): AuthenticationEvent()
}
