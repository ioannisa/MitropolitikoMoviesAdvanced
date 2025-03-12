package eu.anifantakis.mitropolitikomoviesadvanced

import android.app.Application
import eu.anifantakis.mitropolitikomoviesadvanced.di.core.appModule
import eu.anifantakis.mitropolitikomoviesadvanced.di.movies.moviesModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AppApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@AppApplication)
            modules(appModule, moviesModule)
        }
    }
}