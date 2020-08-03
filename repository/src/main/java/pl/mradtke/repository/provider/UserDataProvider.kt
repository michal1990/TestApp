package pl.mradtke.repository.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pl.mradtke.model.api.AvatarListResponse
import pl.mradtke.model.api.UserListResponse
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.api.ResultWrapper
import pl.mradtke.repository.api.safeApiCall

/**
 * Provider for users data.
 *
 * @author Michał Radtke
 * @version 28.07.2020
 */
class UserDataProvider(private val client: ApiClient = ApiClient(),
                       private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    /**
     * Fetch users in a safe way.
     */
    suspend fun fetchUsers(): ResultWrapper<UserListResponse> = safeApiCall(dispatcher) {
        client.usersApi.getUsers()
    }

    /**
     * Fetch avatars in a safe way.
     */
    suspend fun fetchAvatars(): ResultWrapper<AvatarListResponse> = safeApiCall(dispatcher) {
        client.avatarsApi.getAvatars()
    }
}
