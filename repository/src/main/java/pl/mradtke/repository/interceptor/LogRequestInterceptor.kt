package pl.mradtke.repository.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor that logs every proceeded REST request.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
class LogRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("REST call", "${request.url().url()}")
        return chain.proceed(request)
    }
}
