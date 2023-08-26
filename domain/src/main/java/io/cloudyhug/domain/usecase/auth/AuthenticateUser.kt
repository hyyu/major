package io.cloudyhug.domain.usecase.auth

import android.util.Log
import io.cloudyhug.domain.model.error.ErrorResponseException
import io.cloudyhug.data.repository.auth.AuthRepository
import io.cloudyhug.data.repository.credentials.CredentialsGenerator
import io.cloudyhug.data.repository.token.TokenManager
import io.cloudyhug.domain.model.error.toModel
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val authRepository: AuthRepository,
    private val credentialsGenerator: CredentialsGenerator,
    private val tokenManager: TokenManager,
) {

    suspend operator fun invoke(login: String, password: String): Result<Unit> = runCatching {
        credentialsGenerator.generateCredentials(login, password).let { credentials ->
            Log.v("authenticate", "Request body: $credentials")
            authRepository.auhenticateUser(credentials).let { result ->
                result.exceptionOrNull()?.let { cause ->
                    throw cause
                }
                result.getOrNull()?.let { response ->
                    when (response.statusCode) {
                        200 -> {
                            val tokens = response.onSuccess.invoke()
                            tokenManager.saveRefreshToken(tokens.refreshToken)
                            tokenManager.saveAccessToken(tokens.accessToken)
                        }
                        else -> throw ErrorResponseException(
                            error = response.onError.invoke().toModel()
                        )
                    }
                } ?: throw Exception("Request result was not found")
            }
        }
    }

}
