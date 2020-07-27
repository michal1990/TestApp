package pl.mradtke.repository.provider

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.mradtke.model.api.AvatarListResponse
import pl.mradtke.model.api.AvatarResponseItem
import pl.mradtke.model.api.UserItemResponse
import pl.mradtke.model.api.UserListResponse
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.RepositoryAndroidTest
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi

/**
 * @author Michał Radtke
 * @version 27.07.2020
 */
@RunWith(AndroidJUnit4::class)
class UserDataProviderTest : RepositoryAndroidTest() {

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

        runBlocking {
            //When
            val result = dataProvider.fetchUsers()

            //Then
            result.size shouldBe 2

            val testFirstItem = result.first()
            testFirstItem.avatarUrl shouldBe "https://www.deadpool.com/image.png"
            testFirstItem.username shouldBe "Deadpool"
            testFirstItem.userUrl shouldBe "https://www.deadpool.com"
        }
    }

    @Test
    fun whenFetchedNumberOfUsersIsLowerThanAvatarsThenResultListIsLimitedByUserList() {
        //Given
        coEvery { avatarsApiMock.getAvatars() } returns avatarListResponse
        userListResponse.removeAt(1)
        coEvery { usersApiMock.getUsers() } returns userListResponse

        runBlocking {
            //When
            val result = dataProvider.fetchUsers()

            //Then
            result.size shouldBe 1

            val testFirstItem = result.first()
            testFirstItem.avatarUrl shouldBe "https://www.deadpool.com/image.png"
            testFirstItem.username shouldBe "Deadpool"
            testFirstItem.userUrl shouldBe "https://www.deadpool.com"
        }
    }
}
