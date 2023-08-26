package io.cloudyhug.data.network.model.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponseDto(
    @SerialName("access_token")
    val accessToken: String
)
