package pl.mradtke.testapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.mradtke.model.api.AvatarListResponse
import pl.mradtke.model.api.AvatarResponseItem
import pl.mradtke.model.api.UserItemResponse
import pl.mradtke.model.api.UserListResponse
import pl.mradtke.model.ui.UserItem
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi
import pl.mradtke.repository.provider.UserDataProvider

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserListFragmentViewModelTest {

    private lateinit var dataProvider: UserDataProvider
    private val avatarsApiMock = mockk<IAvatarsApi>()
    private val usersApiMock = mockk<IUsersApi>()

    private val userListResponse = UserListResponse().apply {
        add(UserItemResponse("https://www.deadpool.com"))
        add(UserItemResponse("https://www.thanospower.com"))
    }

    private val avatarListResponse = AvatarListResponse(listOf(
            AvatarResponseItem("https://www.deadpool.com/image.png", "Deadpool"),
            AvatarResponseItem("https://www.thanospower.com/picture.png", "Thanos")))

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val mcClientMock = mockk<ApiClient>()
        dataProvider = UserDataProvider(mcClientMock)
        every { mcClientMock.avatarsApi } returns avatarsApiMock
        every { mcClientMock.usersApi } returns usersApiMock
    }

    @Test
    fun getUserListAndCheckIfDataIsCorrectlyTransformed() {
        //Given
        coEvery { avatarsApiMock.getAvatars() } returns avatarListResponse
        coEvery { usersApiMock.getUsers() } returns userListResponse
        val mockedObserver = mockk<Observer<List<UserItem>>>()
        val expectedResults = listOf(
                UserItem("Deadpool", "https://www.deadpool.com", "https://www.deadpool.com/image.png"),
                UserItem("Thanos", "https://www.thanospower.com", "https://www.thanospower.com/picture.png")
        )

        //When
        val viewModel = UserListFragmentViewModel(dataProvider)
        viewModel.userList.observeForever(mockedObserver)

        //Then
        verify { mockedObserver.onChanged(expectedResults) }
        confirmVerified(mockedObserver)
    }
}
