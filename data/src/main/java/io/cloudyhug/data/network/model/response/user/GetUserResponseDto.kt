package io.cloudyhug.data.network.model.response.user

import io.cloudyhug.data.repository.user.model.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponseDto(
    @SerialName("user")
    val userDto: UserDto
)
