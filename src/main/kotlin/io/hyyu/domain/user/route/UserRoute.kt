package io.hyyu.domain.user.route

import io.hyyu.data.Path
import io.hyyu.data.error.ErrorResponse
import io.hyyu.db.user.UserDataSource
import io.hyyu.db.user.model.UserProperties
import io.hyyu.domain.user.model.response.UserResponse
import io.hyyu.domain.user.model.response.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.user(
    userDataSource: UserDataSource
) {
    authenticate("auth-jwt") {
        get(Path.USER) {
            println("\n+++ GET ${Path.USER} +++\n")

            val principal = call.principal<JWTPrincipal>() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(
                        errorCode = HttpStatusCode.BadRequest.value,
                        message = "Something with your request went wrong, please check your request and try again"
                    )
                )
                return@get
            }

            val clientId = principal.getClaim(UserProperties.CLIENT_ID, String::class)
                ?: run {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = ErrorResponse(
                            errorCode = HttpStatusCode.Unauthorized.value,
                            message = "Your access token isn't valid, please provide a valid access token"
                        )
                    )
                    return@get
                }

            val user = userDataSource.getUserByClientId(clientId)
                ?: run {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = ErrorResponse(
                            errorCode = HttpStatusCode.BadRequest.value,
                            message = "User not found"
                        )
                    )
                    return@get
                }

            val response = UserResponse(
                user = user.toResponse()
            )

            println("\n+++ SUCCESS ${Path.USER} +++\n")
            println("Response status: ${HttpStatusCode.OK}")
            println("Response: $response")

            call.respond(
                status = HttpStatusCode.OK,
                message = response
            )
        }
    }
}