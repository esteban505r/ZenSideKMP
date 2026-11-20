package com.zenside.app.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.zenside.app.core.db.ZenSideDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual fun getKoinPlatformModule(): Module = module {
    single<SqlDriver> {
        val dbFile = File(System.getProperty("user.home"), "zenside.db")
        val isFirstRun = !dbFile.exists()
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")
        if (isFirstRun) {
            ZenSideDatabase.Schema.create(driver)
        }
        driver
    }
}
