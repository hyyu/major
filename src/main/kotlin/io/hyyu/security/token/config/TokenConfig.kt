package io.hyyu.security.token.config

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresInOffset: Long,
    val secret: String
)