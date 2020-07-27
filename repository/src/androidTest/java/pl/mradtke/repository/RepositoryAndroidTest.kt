package pl.mradtke.repository

import androidx.test.core.app.ApplicationProvider
import org.junit.BeforeClass
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import pl.mradtke.repository.di.kNetworkServiceModule

/**
 * Base android test for repository module with test initial configuration.
 *
 * @version 27.07.2020
 * @author Michał Radtke
 */
open class RepositoryAndroidTest : KoinTest {

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            startKoin {
                androidContext(ApplicationProvider.getApplicationContext())
                modules(kNetworkServiceModule)
            }
        }
    }
}
