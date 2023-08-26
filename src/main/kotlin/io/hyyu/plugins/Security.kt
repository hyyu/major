package io.hyyu.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.hyyu.data.error.ErrorResponse
import io.hyyu.security.token.config.TokenConfig
import io.hyyu.db.user.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity(
    userDataSource: UserDataSource,
    config: TokenConfig
) {
    authentication {
        jwt("auth-jwt") {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { jwtCredential ->
                userDataSource.getUserByClientId(
                    jwtCredential.payload.getClaim("client_id").asString()
                )?.let {
                    val expirationDate = jwtCredential.payload.getClaim("exp").asLong()
                    val currentTimestamp = System.currentTimeMillis()

                    if (expirationDate <= currentTimestamp)
                        JWTPrincipal(jwtCredential.payload)
                    else
                        return@validate null
                }
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ErrorResponse(
                        errorCode = HttpStatusCode.Unauthorized.value,
                        message = "Token is invalid. A call to /refreshToken is required"
                    )
                )
            }
        }
    }
}
