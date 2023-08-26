package io.cloudyhug.data.repository.auth.impl

import io.cloudyhug.data.network.model.response.Response
import io.cloudyhug.data.network.model.response.auth.AuthenticateResponseDto
import io.cloudyhug.data.network.model.response.auth.RegisterResponseDto
import io.cloudyhug.data.network.route.Endpoints
import io.cloudyhug.data.repository.auth.AuthRepository
import io.cloudyhug.data.repository.credentials.dto.CredentialsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val client: HttpClient,
) : AuthRepository {

    override suspend fun registerUser(credentials: CredentialsDto): Result<Response<RegisterResponseDto>> =
        runCatching {
            withContext(Dispatchers.IO) {
                client.post(Endpoints.REGISTER) {
                    setBody(credentials)
                }.let { httpResponse ->
                    Response(
                        statusCode = httpResponse.status.value,
                        onSuccess = { httpResponse.body() },
                        onError = { httpResponse.body() }
                    )
                }
            }
        }

    override suspend fun auhenticateUser(credentials: CredentialsDto): Result<Response<AuthenticateResponseDto>> =
        runCatching {
            withContext(Dispatchers.IO) {
                client.post(Endpoints.CONNECT) {
                    setBody(credentials)
                }.let { httpResponse ->
                    Response(
                        statusCode = httpResponse.status.value,
                        onSuccess = { httpResponse.body() },
                        onError = { httpResponse.body() }
                    )
                }
            }
        }

}
