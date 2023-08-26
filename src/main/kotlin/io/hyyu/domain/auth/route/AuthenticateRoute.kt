package io.hyyu.domain.auth.route

import io.hyyu.data.Path
import io.hyyu.domain.auth.model.request.AuthRequest
import io.hyyu.domain.auth.model.response.AuthResponse
import io.hyyu.data.error.ErrorResponse
import io.hyyu.security.hashing.HashingService
import io.hyyu.security.hashing.model.SaltedHash
import io.hyyu.security.token.TokenService
import io.hyyu.security.token.claim.TokenClaim
import io.hyyu.security.token.config.TokenConfig
import io.hyyu.db.user.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticate(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post(Path.CONNECT) {
        println("\n+++ POST ${Path.CONNECT} +++\n")

        val request = call.receiveNullable<AuthRequest>() ?: run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.BadRequest.value,
                    message = "Something with your request went wrong, please check your request and try again"
                )
            )
            return@post
        }

        val user = userDataSource.getUserByClientId(request.clientId)
            ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(
                        errorCode = HttpStatusCode.BadRequest.value,
                        message = "Client id not found, please register before signing in"
                    )
                )
                return@post
            }

        verifyPassword(
            hashingService = hashingService,
            password = request.password,
            userPassword = user.password,
            salt = user.salt
        )?.let {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.BadRequest.value,
                    message = "Incorrect username or password"
                )
            )
            return@post
        }

        val accessToken = tokenService.generate(
            config = tokenConfig,
            claims = arrayOf(
                TokenClaim(
                    name = "user_id",
                    value = user.id.toString()
                ),
                TokenClaim(
                    name = "client_id",
                    value = user.clientId
                )
            )
        )

        val response = AuthResponse(
            accessToken = accessToken,
            refreshToken = user.refreshToken
        )

        println("\n+++ SUCCESS ${Path.CONNECT} +++\n")
        println("Response status: ${HttpStatusCode.OK}")
        println("Response: $response")

        call.respond(
            status = HttpStatusCode.OK,
            message = response
        )
    }
}

private fun verifyPassword(
    hashingService: HashingService,
    password: String,
    userPassword: String,
    salt: String
): String? {
    return if (
        hashingService.verify(
            value = password,
            saltedHash = SaltedHash(
                hash = userPassword,
                salt = salt
            )
        )
    ) null
    else "Incorrect username or password"
}
