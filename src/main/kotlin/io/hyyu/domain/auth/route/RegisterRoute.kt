package io.hyyu.domain.auth.route

import io.hyyu.data.Path
import io.hyyu.domain.auth.model.request.AuthRequest
import io.hyyu.domain.auth.model.response.RegisterResponse
import io.hyyu.data.error.ErrorResponse
import io.hyyu.security.hashing.HashingService
import io.hyyu.db.user.UserDataSource
import io.hyyu.db.user.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.register(
    userDataSource: UserDataSource,
    hashingService: HashingService
) {
    post(Path.REGISTER) {
        println("\n+++ POST ${Path.REGISTER} +++\n")

        val request = call.receiveNullable<AuthRequest>() ?: run {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.InternalServerError.value,
                    message = "Something with your request went wrong, please check your request and try again"
                )
            )
            return@post
        }

        validateRequest(request)?.let { error ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.BadRequest.value,
                    message = error
                )
            )
            return@post
        }

        findUser(
            userDataSource = userDataSource,
            clientId = request.clientId,
            username = request.login
        )?.let { error ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.BadRequest.value,
                    message = error
                )
            )
            return@post
        }

        insertNewUser(
            userDataSource = userDataSource,
            hashingService = hashingService,
            request = request
        )?.let { error ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    errorCode = HttpStatusCode.InternalServerError.value,
                    message = error
                )
            )
            return@post
        }

        val response = RegisterResponse(
            message = "User registered successfully"
        )

        println("\n+++ SUCCESS ${Path.REGISTER} +++\n")
        println("Response status: ${HttpStatusCode.OK}")
        println("Response: $response")

        call.respond(
            status = HttpStatusCode.OK,
            message = response
        )
    }
}

private suspend fun insertNewUser(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    request: AuthRequest
): String? {
    val passwordHash = hashingService.generateSaltedHash(request.password)
    val refreshToken = hashingService.generateSaltedHash(
        value = request.clientId,
        salt = passwordHash.salt
    )
    val user = User(
        username = request.login,
        password = passwordHash.hash,
        clientId = request.clientId,
        salt = passwordHash.salt,
        refreshToken = refreshToken.hash
    )

    return if (userDataSource.insertUser(user)) null else "User could not be created, sorry for that. Please try again later"
}

private fun validateRequest(
    request: AuthRequest
): String? {
    val areFieldsBlank = request.login.isBlank() || request.password.isBlank()
    val isClientIdMissing = request.clientId.isBlank()

    return when {
        isClientIdMissing -> "Client id not provided. Please check your client id."
        areFieldsBlank -> "Your credentials are empty. Please provide correct credentials."
        else -> null
    }
}

private suspend fun findUser(
    userDataSource: UserDataSource,
    clientId: String,
    username: String
): String? = userDataSource.getUserByClientId(clientId)?.let {
    "Your device is already used. Please use your associated account or another device."
} ?: userDataSource.getUserByUsername(username)?.let {
    "Your login is already used. Please use another login."
}
