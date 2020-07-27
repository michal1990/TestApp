package pl.mradtke.testapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import pl.mradtke.repository.di.kNetworkServiceModule

/**
 * @author Michał Radtke
 * @version 27.07.2020
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(listOf(kNetworkServiceModule))
        }
    }
}
