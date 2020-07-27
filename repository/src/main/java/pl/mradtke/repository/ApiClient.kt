package pl.mradtke.repository

import org.koin.core.KoinComponent
import org.koin.core.inject
import pl.mradtke.repository.api.IAvatarsApi
import pl.mradtke.repository.api.IUsersApi

/**
 * API rest client.
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */
class ApiClient : KoinComponent {

    val usersApi: IUsersApi by inject()
    val avatarsApi: IAvatarsApi by inject()
}
