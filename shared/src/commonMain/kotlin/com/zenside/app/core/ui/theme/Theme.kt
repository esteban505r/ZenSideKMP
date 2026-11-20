package com.zenside.app.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ZenColorScheme = lightColorScheme(
    primary = ZenLavender,
    onPrimary = ZenSurface,
    primaryContainer = ZenLavenderContainer,
    onPrimaryContainer = ZenLavenderDark,
    secondary = ZenMint,
    secondaryContainer = ZenMintContainer,
    background = ZenBackground,
    onBackground = ZenOnSurface,
    surface = ZenSurface,
    onSurface = ZenOnSurface,
    surfaceVariant = ZenSurfaceVariant,
    onSurfaceVariant = ZenOnSurfaceVariant,
    outline = ZenOutline,
    error = ZenError
)

private val ZenShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

private val ZenTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
    bodyMedium = TextStyle(fontSize = 15.sp),
    bodySmall = TextStyle(fontSize = 13.sp)
)

@Composable
fun ZenSideTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ZenColorScheme,
        shapes = ZenShapes,
        typography = ZenTypography,
        content = content
    )
}
