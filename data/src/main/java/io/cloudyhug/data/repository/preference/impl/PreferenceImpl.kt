package io.cloudyhug.data.repository.preference.impl

import android.content.SharedPreferences
import android.os.Build
import io.cloudyhug.data.repository.preference.Preference
import javax.inject.Inject

class PreferenceImpl @Inject constructor(private val prefs: SharedPreferences): Preference {

    companion object Endpoints {
        private const val PREF_SESSION_DEVICE_NAME_TAG = "SESSION_DEVICE_NAME"
        private const val PREF_SESSION_REFRESH_TOKEN_TAG = "SESSION_REFRESH_TOKEN"
        private const val PREF_SESSION_ACCESS_TOKEN_TAG = "SESSION_ACCESS_TOKEN"
    }

    override fun getClientId(): String? = prefs.getString(PREF_SESSION_DEVICE_NAME_TAG, null)

    override fun saveClientId(): String {
        val newClientId = generateClientIdString()

        prefs.edit()
            .putString(PREF_SESSION_DEVICE_NAME_TAG, newClientId)
            .apply()

        return newClientId
    }

    override fun getAccessToken(): String? = prefs.getString(PREF_SESSION_ACCESS_TOKEN_TAG, null)
    override fun getRefreshToken(): String? = prefs.getString(PREF_SESSION_REFRESH_TOKEN_TAG, null)

    override fun saveAccessToken(value: String) {
        prefs.edit()
            .putString(PREF_SESSION_ACCESS_TOKEN_TAG, value)
            .apply()
    }

    override fun saveRefreshToken(value: String) {
        prefs.edit()
            .putString(PREF_SESSION_REFRESH_TOKEN_TAG, value)
            .apply()
    }

}

private fun generateClientIdString() = "${Build.MANUFACTURER}-${Build.MODEL} ${System.currentTimeMillis()}"
