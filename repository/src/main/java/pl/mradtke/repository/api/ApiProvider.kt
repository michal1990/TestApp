package pl.mradtke.repository.api

import android.util.Base64

/**
 * API data provider. Sensitive API data is protected.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
class ApiProvider {

    private companion object {
        init {
            System.loadLibrary("KeysProvider")
        }
    }

    private external fun getUsersApiSecureHostname(): String
    private external fun getAvatarsApiSecureHostname(): String

    /**
     * Get users API hostname.
     */
    fun getUsersApiHostname(): String = getUsersApiSecureHostname().decode()

    /**
     * Get avatars API hostname.
     */
    fun getAvatarsApiHostname(): String = getAvatarsApiSecureHostname().decode()

    private fun String.decode(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(Charsets.UTF_8)
    }
}