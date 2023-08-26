package io.hyyu.security.hashing

import io.hyyu.security.hashing.model.SaltedHash

interface HashingService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash
    fun generateSaltedHash(value: String, salt: String, saltLength: Int = 32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}