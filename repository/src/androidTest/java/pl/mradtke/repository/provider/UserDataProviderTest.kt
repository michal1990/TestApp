package pl.mradtke.repository.provider

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.mradtke.model.api.AvatarListResponse
import pl.mradtke.model.api.AvatarResponseItem
import pl.mradtke.model.api.UserItemResponse
import pl.mradtke.model.api.UserListResponse
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi
import pl.mradtke.repository.api.ResultWrapper
import java.io.IOException

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserDataProviderTest  {

    private lateinit var dataProvider: UserDataProvider
    private val testDispatcher = TestCoroutineDispatcher()
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
        dataProvider = UserDataProvider(mcClientMock, testDispatcher)
        every { mcClientMock.avatarsApi } returns avatarsApiMock
        every { mcClientMock.usersApi } returns usersApiMock
    }

    @Test
    fun getUserListWithSuccessResponse() {
        //Given
        coEvery { usersApiMock.getUsers() } returns userListResponse

        runBlockingTest {
            //When
            val result = dataProvider.fetchUsers()

            //Then
            assert(result is ResultWrapper.Success)
            result as ResultWrapper.Success
            result.value.size shouldBe 2

            val testFirstItem = result.value.first()
            testFirstItem.url shouldBe "https://www.deadpool.com"
        }
    }

    @Test
    fun getUserListWithErrorResponse() {
        //Given
        coEvery { usersApiMock.getUsers() } throws IOException()

        runBlockingTest {
            //When
            val result = dataProvider.fetchUsers()

            //Then
            assert(result is ResultWrapper.Failure)
        }
    }

    @Test
    fun getAvatarsListWithSuccessResponse() {
        //Given
        coEvery { avatarsApiMock.getAvatars() } returns avatarListResponse

        runBlockingTest {
            //When
            val result = dataProvider.fetchAvatars()

            //Then
            assert(result is ResultWrapper.Success)
            result as ResultWrapper.Success
            result.value.list.size shouldBe 2

            val testFirstItem = result.value.list.first()
            testFirstItem.avatarUrl shouldBe "https://www.deadpool.com/image.png"
            testFirstItem.username shouldBe "Deadpool"
        }
    }

    @Test
    fun getAvatarsListWithErrorResponse() {
        //Given
        coEvery { avatarsApiMock.getAvatars() } throws IOException()

        runBlockingTest {
            //When
            val result = dataProvider.fetchAvatars()

            //Then
            assert(result is ResultWrapper.Failure)
        }
    }
}
