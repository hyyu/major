package io.hyyu.domain.user.model.response

import io.hyyu.db.user.model.User
import io.hyyu.db.user.model.UserProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("user")
    val user: UserResponseData
)

@Serializable
data class UserResponseData(
    @SerialName(UserProperties.USERNAME)
    val username: String,
    @SerialName(UserProperties.REFRESH_TOKEN)
    val refreshToken: String
)

fun User.toResponse() = UserResponseData(
    username = username,
    refreshToken = refreshToken
)
