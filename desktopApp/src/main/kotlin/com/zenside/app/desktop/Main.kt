package com.zenside.app.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.zenside.app.core.di.initKoin
import com.zenside.app.core.navigation.DefaultRootComponent
import com.zenside.app.core.navigation.ZenSideApp
import org.koin.core.Koin

/**
 * JVM entry point — hosts ONLY the shared Compose root composable, no business logic.
 */
fun main() {
    val koin: Koin = initKoin()

    application {
        val rootComponent = remember {
            val lifecycle = LifecycleRegistry()
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
                koin = koin
            ).also { lifecycle.resume() }
        }

        Window(onCloseRequest = ::exitApplication, title = "ZenSide") {
            ZenSideApp(rootComponent)
        }
    }
}
