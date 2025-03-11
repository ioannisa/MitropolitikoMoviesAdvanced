package eu.anifantakis.mitropolitikomoviesadvanced.di.core

import eu.anifantakis.lib.securepersist.PersistManager
import eu.anifantakis.mitropolitikomoviesadvanced.AppApplication
import eu.anifantakis.mitropolitikomoviesadvanced.BuildConfig
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Allow Android Secure Persist Injection
    single<PersistManager> {
        PersistManager(androidContext(), "${BuildConfig.APPLICATION_ID}.securedPersistence")
    }

    // Provide Application Coroutine Scope for usage where global scope is needed (like db queries)
    single<CoroutineScope> {
        (androidApplication() as AppApplication).applicationScope
    }
}