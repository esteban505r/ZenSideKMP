package com.zenside.app.core.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/** Shared Compose root — the only composable platform entry points should host. */
@Composable
fun ZenSideApp(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(rootComponent)
    }
}
