package io.hyyu

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.hyyu.plugins.configureRouting
import io.hyyu.plugins.configureSecurity
import io.hyyu.plugins.configureSerialization
import io.hyyu.security.hashing.impl.SHA256HashingService
import io.hyyu.security.token.config.TokenConfig
import io.hyyu.security.token.impl.JwtTokenService
import io.hyyu.db.user.impl.MongoUserDataSource
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

const val EXPIRES_IN_OFFSET = 15L* 60L * 1000L

fun Application.module() {
    val dbName = System.getenv("MONGO_NAME")
    val mongoPw = System.getenv("MONGO_PW")
    val db = MongoClient
        .create(
            connectionString = "mongodb+srv://hyyu:$mongoPw@cluster0.rl2umel.mongodb.net/$dbName?retryWrites=true&w=majority"
        )
        .getDatabase(dbName)

    configureApp(db)
}

private fun Application.configureApp(
    db: MongoDatabase
) {
    val userDataSource = MongoUserDataSource(db)
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresInOffset = EXPIRES_IN_OFFSET,
        secret = System.getenv("JWT_SECRET")
    )

    println("\n+++ READY +++\n")

    configureSecurity(
        userDataSource = userDataSource,
        config = tokenConfig
    )
    configureSerialization()
    configureRouting(
        userDataSource = userDataSource,
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}
