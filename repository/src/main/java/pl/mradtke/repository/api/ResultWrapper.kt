package pl.mradtke.repository.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * API result wrapper. It helps to distinguish success and failure request results.
 *
 * @author Michał Radtke
 * @version 28.07.2020
 */
sealed class ResultWrapper<out T> {
    /**
     * Success response type. It stores the result.
     */
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    /**
     * Failure response type. It stores the error message.
     */
    data class Failure(val message: String?) : ResultWrapper<Nothing>()
}

/**
 * Call any API in a safe way. Errors will be catch and provided with the result.
 */
suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.Failure(throwable.message)
        }
    }
}
