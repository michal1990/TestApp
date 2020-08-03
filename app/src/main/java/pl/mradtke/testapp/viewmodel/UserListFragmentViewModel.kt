package pl.mradtke.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import pl.mradtke.model.api.AvatarListResponse
import pl.mradtke.model.api.UserListResponse
import pl.mradtke.model.ui.UserItem
import pl.mradtke.repository.api.ResultWrapper
import pl.mradtke.repository.provider.UserDataProvider

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
class UserListFragmentViewModel(private val dataProvider: UserDataProvider) : ViewModel(), KoinComponent {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _requestError = MutableLiveData<String>()
    val requestError: LiveData<String> get() = _requestError

    init {
        _isLoading.value = true
    }

    val userList: LiveData<List<UserItem>> = liveData(Dispatchers.IO) {
        val usersResponse = dataProvider.fetchUsers()
        val avatarsResponse = dataProvider.fetchAvatars()

        if (usersResponse is ResultWrapper.Success && avatarsResponse is ResultWrapper.Success) {
            val usersWithAvatars = mixUsersWithAvatars(usersResponse.value, avatarsResponse.value)
            _isLoading.postValue(false)
            emit(usersWithAvatars)
        } else {
            _isLoading.postValue(false)
            _requestError.postValue("Something went wrong during API calls: Users: ${(usersResponse as? ResultWrapper.Failure)?.message}, " +
                    "Avatars: ${(avatarsResponse as? ResultWrapper.Failure)?.message}")
        }
    }

    private fun mixUsersWithAvatars(users: UserListResponse, avatars: AvatarListResponse): List<UserItem> = mutableListOf<UserItem>().apply {
        avatars.list.mapIndexed { i, avatarItem ->
            if (i == users.size)
                return@mapIndexed
            this.add(UserItem(avatarItem.username, users[i].url, avatarItem.avatarUrl))
        }
    }
}
