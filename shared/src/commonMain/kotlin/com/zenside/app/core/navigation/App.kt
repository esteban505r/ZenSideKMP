package com.zenside.app.core.navigation

import androidx.compose.runtime.Composable
import com.zenside.app.core.ui.theme.ZenSideTheme

/** Shared Compose root — the only composable platform entry points should host. */
@Composable
fun ZenSideApp(rootComponent: RootComponent) {
    ZenSideTheme {
        RootContent(rootComponent)
    }
}
