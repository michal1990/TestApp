package pl.mradtke.repository.api

import pl.mradtke.model.api.AvatarListResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Avatars API.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
interface IAvatarsApi {

    companion object {
        const val AVATAR_360_URL_FIELD_NAME = "avatar_360_url"
        const val USERNAME_FIELD_NAME = "username"
    }

    /**
     * Get avatar list with a given fields.
     *
     * Example: host/users?fields=avatar_360_url,username
     */
    @GET("users")
    suspend fun getAvatars(
            @Query("fields") fields: String = "$AVATAR_360_URL_FIELD_NAME,$USERNAME_FIELD_NAME"
    ): AvatarListResponse
}
