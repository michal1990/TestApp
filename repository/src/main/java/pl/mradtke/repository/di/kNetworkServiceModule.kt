package pl.mradtke.repository.di

import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi
import pl.mradtke.repository.interceptor.LogRequestInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network service Koin module.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */

private const val USERS_API_SCOPE_NAME = "UsersApiScopeName"
private const val AVATARS_API_SCOPE_NAME = "AvatarsApiScopeName"

val kNetworkServiceModule = module {
    single { ApiClient() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(LogRequestInterceptor())
            .build()
    }

    single(named(USERS_API_SCOPE_NAME)) {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named(AVATARS_API_SCOPE_NAME)) {
        Retrofit.Builder()
            .baseUrl("https://api.dailymotion.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named(USERS_API_SCOPE_NAME)).create(IUsersApi::class.java) }
    single { get<Retrofit>(named(AVATARS_API_SCOPE_NAME)).create(IAvatarsApi::class.java) }
}
