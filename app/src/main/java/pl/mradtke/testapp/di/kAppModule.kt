package pl.mradtke.testapp.di

import org.koin.dsl.module
import pl.mradtke.testapp.viewmodel.UserListFragmentViewModel

/**
 * Main app Koin module.
 *
 * @author Michał Radtke
 * @version 28.07.2020
 */
val kAppModule = module {
    factory { UserListFragmentViewModel(get()) }
}
