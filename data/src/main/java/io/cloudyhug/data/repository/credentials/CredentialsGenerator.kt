package io.cloudyhug.data.repository.credentials

import io.cloudyhug.data.repository.credentials.dto.CredentialsDto

interface CredentialsGenerator {
    fun generateCredentials(login: String, password: String): CredentialsDto
}
