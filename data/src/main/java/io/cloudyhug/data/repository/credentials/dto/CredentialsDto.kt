package io.cloudyhug.data.repository.credentials.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsDto(
    @SerialName("login")
    var login: String,
    @SerialName("password")
    var password: String,
    @SerialName("client_id")
    var clientId: String = ""
)
