package io.hyyu.security.token.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.hyyu.security.token.TokenService
import io.hyyu.security.token.claim.TokenClaim
import io.hyyu.security.token.config.TokenConfig
import java.util.*

class JwtTokenService : TokenService {
    override fun generate(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(
                Date(System.currentTimeMillis() + config.expiresInOffset)
            )
        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }
}
