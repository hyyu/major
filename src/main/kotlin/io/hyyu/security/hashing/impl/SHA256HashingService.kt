package io.hyyu.security.hashing.impl

import io.hyyu.security.hashing.HashingService
import io.hyyu.security.hashing.model.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class SHA256HashingService : HashingService {
    private fun generateHash(salt: String, value: String) = DigestUtils.sha256Hex("$salt$value")

    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = generateHash(saltAsHex, value)

        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override fun generateSaltedHash(value: String, salt: String, saltLength: Int): SaltedHash = SaltedHash(
        hash = generateHash(salt, value),
        salt = salt
    )

    override fun verify(value: String, saltedHash: SaltedHash): Boolean = generateHash(saltedHash.salt, value) == saltedHash.hash

}
