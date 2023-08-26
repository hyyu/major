package io.cloudyhug.data.repository.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("username")
    val username: String,
    @SerialName("refresh_token")
    val refreshToken: String
)