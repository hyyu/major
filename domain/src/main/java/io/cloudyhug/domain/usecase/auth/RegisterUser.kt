package io.cloudyhug.domain.usecase.auth

import android.util.Log
import io.cloudyhug.data.repository.auth.AuthRepository
import io.cloudyhug.data.repository.credentials.CredentialsGenerator
import io.cloudyhug.domain.model.error.ErrorResponseException
import io.cloudyhug.domain.model.error.toModel
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val authRepository: AuthRepository,
    private val credentialsGenerator: CredentialsGenerator,
) {

    suspend operator fun invoke(login: String, password: String): Result<String> = runCatching {
        credentialsGenerator.generateCredentials(login, password).let { credentials ->
            Log.v("register", "Request body: $credentials")
            authRepository.registerUser(credentials).let { result ->
                result.exceptionOrNull()?.let { cause ->
                    throw cause
                }
                result.getOrNull()?.let { response ->
                    when (response.statusCode) {
                        200 -> response.onSuccess.invoke().message
                        else -> throw ErrorResponseException(
                            error = response.onError.invoke().toModel()
                        )
                    }
                } ?: throw Exception("Request result was not found")
            }
        }
    }

}
