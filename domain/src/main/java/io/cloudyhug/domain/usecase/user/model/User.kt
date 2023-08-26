package io.cloudyhug.domain.usecase.user.model

import io.cloudyhug.data.network.model.response.user.GetUserResponseDto

data class User(
    val username: String,
    val refreshToken: String
)

fun GetUserResponseDto.toModel() = User(
    username = userDto.username,
    refreshToken = userDto.refreshToken
)
