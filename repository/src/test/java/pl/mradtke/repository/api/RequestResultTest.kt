package pl.mradtke.repository.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
@ExperimentalCoroutinesApi
class RequestResultTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlockingTest {
            val lambdaResult = true
            val result = safeApiCall(testDispatcher) { lambdaResult }
            assertEquals(ResultWrapper.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws exception then it should emit the result as failure`() {
        runBlockingTest {
            val result = safeApiCall(testDispatcher) { throw IOException("No internet connection") }
            assertEquals(ResultWrapper.Failure("No internet connection"), result)
        }
    }
}
