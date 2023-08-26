package io.hyyu.plugins

import io.hyyu.domain.auth.route.authenticate
import io.hyyu.domain.auth.route.register
import io.hyyu.security.hashing.HashingService
import io.hyyu.security.token.TokenService
import io.hyyu.security.token.config.TokenConfig
import io.hyyu.db.user.UserDataSource
import io.hyyu.domain.user.route.refreshToken
import io.hyyu.domain.user.route.user
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        register(
            userDataSource = userDataSource,
            hashingService = hashingService
        )
        authenticate(
            userDataSource = userDataSource,
            hashingService = hashingService,
            tokenService = tokenService,
            tokenConfig = tokenConfig
        )
        user(
            userDataSource = userDataSource
        )
        refreshToken(
            userDataSource = userDataSource,
            tokenService = tokenService,
            tokenConfig = tokenConfig
        )
    }
}
