package com.zenside.app.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zenside.app.core.db.ZenSideDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/** Holds the Android [Context] needed by the SQLDelight driver. Set once from Application/MainActivity. */
object AndroidPlatformContext {
    lateinit var appContext: Context
}

actual fun getKoinPlatformModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(ZenSideDatabase.Schema, AndroidPlatformContext.appContext, "zenside.db")
    }
}
