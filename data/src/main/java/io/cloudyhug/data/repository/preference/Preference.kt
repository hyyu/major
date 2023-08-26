package io.cloudyhug.data.repository.preference

interface Preference {

    /* Client ID */
    fun getClientId(): String?
    fun saveClientId(): String

    /* Access Token */
    fun getAccessToken(): String?
    fun saveAccessToken(value: String)

    /* Refresh Token */
    fun getRefreshToken(): String?
    fun saveRefreshToken(value: String)

}
