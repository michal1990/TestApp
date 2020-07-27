package pl.mradtke.repository.api

import pl.mradtke.model.api.UserListResponse
import retrofit2.http.GET

/**
 * Users API.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
interface IUsersApi {

    /**
     * Get user list.
     *
     * Example: host/users
     */
    @GET("users")
    suspend fun getUsers(): UserListResponse
}
