package io.cloudyhug.major.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Beige80,
    onPrimary = Beige20,
    primaryContainer = Beige30,
    onPrimaryContainer = Beige90,
    secondary = Brown80,
    onSecondary = Brown20,
    secondaryContainer = Brown30,
    onSecondaryContainer = Brown90,
    tertiary = Green80,
    onTertiary = Green20,
    tertiaryContainer = Green30,
    onTertiaryContainer = Green90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    surface = Gray6,
    onSurface = Gray90,
    background = Gray6,
    onBackground = Gray90
)

private val LightColorScheme = lightColorScheme(
    primary = Beige40,
    onPrimary = White,
    primaryContainer = Beige90,
    onPrimaryContainer = Beige10,
    secondary = Brown40,
    onSecondary = White,
    secondaryContainer = Brown90,
    onSecondaryContainer = Brown10,
    tertiary = Green40,
    onTertiary = White,
    tertiaryContainer = Green90,
    onTertiaryContainer = Green10,
    error = Red40,
    onError = White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    surface = Gray98,
    onSurface = Gray10,
    background = Gray98,
    onBackground = Gray10
)

@Composable
fun MajorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}