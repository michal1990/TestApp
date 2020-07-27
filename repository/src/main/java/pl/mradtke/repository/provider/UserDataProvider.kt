package pl.mradtke.repository.provider

import pl.mradtke.model.ui.UserItem
import pl.mradtke.repository.ApiClient

/**
 * Provider for users data.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
class UserDataProvider(private val client: ApiClient = ApiClient()) {

    /**
     * Fetch user list.
     */
    suspend fun fetchUsers(): List<UserItem> {
        val users = client.usersApi.getUsers()
        val avatars = client.avatarsApi.getAvatars()
        val results = mutableListOf<UserItem>()

        avatars.list.mapIndexed { i, avatarItem ->
            if (i == users.size)
                return@mapIndexed
            results.add(UserItem(avatarItem.username, users[i].url, avatarItem.avatarUrl))
        }

        return results
    }
}
