package com.donaddie.androiddingo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF637DDA),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD8E2FF),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFF5B6078),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE0E2F2),
    onSecondaryContainer = Color(0xFF181D2F),
    tertiary = Color(0xFF77566D),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD9E8),
    onTertiaryContainer = Color(0xFF2D1227),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFDF8FF),
    onBackground = Color(0xFF1D1B20),
    surface = Color(0xFFFDF8FF),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE4E1EC),
    onSurfaceVariant = Color(0xFF46464F),
    outline = Color(0xFF767680),
    outlineVariant = Color(0xFFC7C5D1),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF322F35),
    inverseOnSurface = Color(0xFFF5EFF7),
    inversePrimary = Color(0xFFB0C4FF),
    surfaceDim = Color(0xFFDED8E0),
    surfaceBright = Color(0xFFFDF8FF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F2FA),
    surfaceContainer = Color(0xFFF1ECF4),
    surfaceContainerHigh = Color(0xFFEBE6EE),
    surfaceContainerHighest = Color(0xFFE6E0E9)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB0C4FF),
    onPrimary = Color(0xFF003259),
    primaryContainer = Color(0xFF1D477E),
    onPrimaryContainer = Color(0xFFD8E2FF),
    secondary = Color(0xFFC4C6DA),
    onSecondary = Color(0xFF2D3146),
    secondaryContainer = Color(0xFF43485E),
    onSecondaryContainer = Color(0xFFE0E2F2),
    tertiary = Color(0xFFE6BDD2),
    onTertiary = Color(0xFF44263A),
    tertiaryContainer = Color(0xFF5C3D52),
    onTertiaryContainer = Color(0xFFFFD9E8),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF141218),
    onBackground = Color(0xFFE6E0E9),
    surface = Color(0xFF141218),
    onSurface = Color(0xFFE6E0E9),
    surfaceVariant = Color(0xFF46464F),
    onSurfaceVariant = Color(0xFFC7C5D1),
    outline = Color(0xFF91909A),
    outlineVariant = Color(0xFF46464F),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE6E0E9),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = Color(0xFF345C93),
    surfaceDim = Color(0xFF141218),
    surfaceBright = Color(0xFF3B383E),
    surfaceContainerLowest = Color(0xFF0F0D13),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B)
)

@Composable
fun DingoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
