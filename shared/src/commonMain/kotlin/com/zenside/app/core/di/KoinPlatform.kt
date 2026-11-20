package com.zenside.app.core.di

import org.koin.core.module.Module

/**
 * Supplies only platform-specific bindings (the SQLDelight [app.cash.sqldelight.db.SqlDriver]
 * today). Implemented per platform in androidMain/iosMain/desktopMain.
 */
expect fun getKoinPlatformModule(): Module
