package io.hyyu.security.hashing.model

data class SaltedHash(
    val hash: String,
    val salt: String
)
