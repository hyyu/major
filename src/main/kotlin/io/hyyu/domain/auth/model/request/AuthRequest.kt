package io.hyyu.domain.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("login")
    var login: String,
    @SerialName("password")
    var password: String,
    @SerialName("client_id")
    var clientId: String
)
