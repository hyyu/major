package io.cloudyhug.data.repository.token.impl

import io.cloudyhug.data.repository.preference.Preference
import io.cloudyhug.data.repository.token.TokenManager
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
    private val prefs: Preference
): TokenManager {
    override fun getAccessToken() = prefs.getAccessToken()
    override fun getRefreshToken() = prefs.getRefreshToken()
    override fun saveAccessToken(accessToken: String) = prefs.saveAccessToken(accessToken)
    override fun saveRefreshToken(refreshToken: String) = prefs.saveRefreshToken(refreshToken)
}
