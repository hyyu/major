package io.cloudyhug.data.repository.auth

import io.cloudyhug.data.network.model.response.Response
import io.cloudyhug.data.network.model.response.auth.AuthenticateResponseDto
import io.cloudyhug.data.network.model.response.auth.RegisterResponseDto
import io.cloudyhug.data.repository.credentials.dto.CredentialsDto

interface AuthRepository {
    suspend fun registerUser(credentials: CredentialsDto): Result<Response<RegisterResponseDto>>
    suspend fun auhenticateUser(credentials: CredentialsDto): Result<Response<AuthenticateResponseDto>>
}
