package com.zenside.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.zenside.app.core.di.AndroidPlatformContext
import com.zenside.app.core.di.initKoin
import com.zenside.app.core.navigation.DefaultRootComponent
import com.zenside.app.core.navigation.ZenSideApp

/**
 * Hosts ONLY the shared Compose root composable — no business logic here.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidPlatformContext.appContext = applicationContext
        val koin = initKoin()

        val rootComponent = DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = essentyLifecycle()),
            koin = koin
        )

        setContent {
            ZenSideApp(rootComponent)
        }
    }
}
