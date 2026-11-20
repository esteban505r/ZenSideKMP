package com.zenside.app.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zenside.app.core.db.ZenSideDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getKoinPlatformModule(): Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(ZenSideDatabase.Schema, "zenside.db")
    }
}
