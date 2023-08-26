package io.cloudyhug.data.network.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.network.model.request.RefreshTokenRequestDto
import io.cloudyhug.data.network.model.response.user.RefreshTokenResponseDto
import io.cloudyhug.data.network.route.Endpoints
import io.cloudyhug.data.repository.token.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideHttpClient(
        json: Json,
        tokenManager: TokenManager
    ): HttpClient {
        val client = HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(ContentNegotiation) {
                json(json)
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = Endpoints.HOST
                    port = Endpoints.PORT
                }
                contentType(ContentType.Application.Json)

                tokenManager.getAccessToken()?.let { accessToken ->
                    headers.append("Authorization", "Bearer $accessToken")
                }
            }
        }

        client.plugin(HttpSend).intercept { request ->
            val originalCall = execute(request)

            when (originalCall.response.status) {
                HttpStatusCode.Unauthorized -> {
                    tokenManager.getRefreshToken()?.let { refreshToken ->
                        val result: Result<RefreshTokenResponseDto> = runCatching {
                            withContext(Dispatchers.IO) {
                                originalCall.client.post(Endpoints.REFRESH_TOKEN) {
                                    setBody(RefreshTokenRequestDto(refreshToken = refreshToken))
                                }.body()
                            }
                        }

                        result.exceptionOrNull()?.let { cause ->
                            Log.e("Refresh Token", "Request failed: cause: $cause")
                            return@intercept originalCall
                        }

                        result.getOrNull()?.let {
                            tokenManager.saveAccessToken(it.accessToken)
                            request.headers.remove("Authorization")
                            request.headers.append("Authorization", "Bearer ${it.accessToken}")
                            return@intercept execute(request)
                        } ?: run {
                            Log.e("Refresh Token", "Request failed: could not refresh your access token")
                            return@intercept originalCall
                        }
                    }
                }
            }
            return@intercept originalCall
        }

        return client
    }

}
