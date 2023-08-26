package io.cloudyhug.data.repository.credentials.impl

import io.cloudyhug.data.repository.preference.Preference
import io.cloudyhug.data.repository.credentials.dto.CredentialsDto
import io.cloudyhug.data.repository.credentials.CredentialsGenerator
import javax.inject.Inject

class CredentialsGeneratorImpl @Inject constructor(
    private val prefs: Preference
) : CredentialsGenerator {
    override fun generateCredentials(login: String, password: String): CredentialsDto = CredentialsDto(login, password, prefs.getClientId() ?: prefs.saveClientId())
}
