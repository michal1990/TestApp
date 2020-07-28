package pl.mradtke.repository.di

import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.mradtke.repository.ApiClient
import pl.mradtke.repository.api.ApiProvider
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi
import pl.mradtke.repository.interceptor.LogRequestInterceptor
import pl.mradtke.repository.provider.UserDataProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network service Koin module.
 *
 * @author Michał Radtke
 * @version 28.07.2020
 */

private const val USERS_API_SCOPE_NAME = "UsersApiScopeName"
private const val AVATARS_API_SCOPE_NAME = "AvatarsApiScopeName"

val kNetworkServiceModule = module {
    single { ApiClient() }
    factory { ApiProvider() }
    factory { UserDataProvider() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(LogRequestInterceptor())
            .build()
    }

    single(named(USERS_API_SCOPE_NAME)) {
        Retrofit.Builder()
            .baseUrl(get<ApiProvider>().getUsersApiHostname())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named(AVATARS_API_SCOPE_NAME)) {
        Retrofit.Builder()
            .baseUrl(get<ApiProvider>().getAvatarsApiHostname())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named(USERS_API_SCOPE_NAME)).create(IUsersApi::class.java) }
    single { get<Retrofit>(named(AVATARS_API_SCOPE_NAME)).create(IAvatarsApi::class.java) }
}
