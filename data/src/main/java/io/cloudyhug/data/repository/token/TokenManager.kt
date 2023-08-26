package io.cloudyhug.data.repository.token

interface TokenManager {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun saveAccessToken(accessToken: String)
    fun saveRefreshToken(refreshToken: String)
}
