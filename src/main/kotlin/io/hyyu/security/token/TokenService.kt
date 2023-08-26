package io.hyyu.security.token

import io.hyyu.security.token.claim.TokenClaim
import io.hyyu.security.token.config.TokenConfig

interface TokenService {
    fun generate(config: TokenConfig, vararg claims: TokenClaim): String
}
