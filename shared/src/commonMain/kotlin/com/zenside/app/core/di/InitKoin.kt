package com.zenside.app.core.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/** Loads the platform module + all feature modules. Call once at app startup. */
fun initKoin(extraModules: List<Module> = emptyList()): Koin {
    val koinApplication = startKoin {
        modules(listOf(getKoinPlatformModule()) + featureModules + extraModules)
    }
    return koinApplication.koin
}
