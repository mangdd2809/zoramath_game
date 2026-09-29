package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ZorraOrangeLight,
    onPrimary = Color.Black,
    primaryContainer = ZorraOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = ZorraSunYellow,
    onSecondary = Color.Black,
    tertiary = ZorraSkyBlue,
    background = Color(0xFF181824),
    surface = Color(0xFF232438),
    onBackground = Color(0xFFF1F1F6),
    onSurface = Color(0xFFF1F1F6)
)

private val LightColorScheme = lightColorScheme(
    primary = ZorraOrange,
    onPrimary = Color.White,
    primaryContainer = ZorraYellowLight,
    onPrimaryContainer = ZorraOrangeDark,
    secondary = ZorraSunYellow,
    onSecondary = Color.Black,
    secondaryContainer = ZorraYellowLight,
    onSecondaryContainer = Color(0xFF6B4800),
    tertiary = ZorraMintGreen,
    onTertiary = Color.White,
    tertiaryContainer = ZorraMintLight,
    onTertiaryContainer = Color(0xFF004D40),
    background = GameBgCream,
    surface = GameCardBg,
    onBackground = GameTextDark,
    onSurface = GameTextDark
)

@Composable
fun ZorraMathTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded fun colors by default!
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
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    ZorraMathTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
