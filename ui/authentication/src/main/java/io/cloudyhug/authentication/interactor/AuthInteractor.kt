package io.cloudyhug.authentication.interactor

import io.cloudyhug.domain.usecase.auth.AuthenticateUser
import io.cloudyhug.domain.usecase.auth.RegisterUser
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val registerUser: RegisterUser,
    private val authenticateUser: AuthenticateUser
) {
    suspend fun registerUser(login: String, password: String): Result<String> = registerUser.invoke(login, password)
    suspend fun authenticateUser(login: String, password: String): Result<Unit> = authenticateUser.invoke(login, password)
}
