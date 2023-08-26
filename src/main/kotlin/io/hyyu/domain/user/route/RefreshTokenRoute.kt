package io.hyyu.domain.user.route

import io.hyyu.data.Path
import io.hyyu.data.error.ErrorResponse
import io.hyyu.db.user.UserDataSource
import io.hyyu.domain.user.model.request.RefreshTokenRequest
import io.hyyu.domain.user.model.response.RefreshTokenResponse
import io.hyyu.security.token.TokenService
import io.hyyu.security.token.claim.TokenClaim
import io.hyyu.security.token.config.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.refreshToken(
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post(Path.REFRESH_TOKEN) {
        println("\n+++ POST ${Path.REFRESH_TOKEN} +++\n")

        val request = call.receiveNullable<RefreshTokenRequest>() ?: run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.BadRequest.value,
                    message = "Something with your request went wrong, please check your request and try again"
                )
            )
            return@post
        }

        val user = userDataSource.getUserByRefreshToken(request.refreshToken)
            ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(
                        errorCode = HttpStatusCode.BadRequest.value,
                        message = "User not found"
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

        val response = RefreshTokenResponse(
            accessToken = accessToken
        )

        println("\n+++ SUCCESS ${Path.REFRESH_TOKEN} +++\n")
        println("Response status: ${HttpStatusCode.OK}")
        println("Response: $response")

        call.respond(
            status = HttpStatusCode.OK,
            message = response
        )

    }
}
