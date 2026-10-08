package com.`is`.rescuepet.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    secondary = GoldLight,
    tertiary = NavyBlueLight,

    background = NavyBlue,
    surface = NavyBlue,

    onPrimary = NavyBlue,
    onSecondary = NavyBlue,
    onTertiary = Cream,

    onBackground = Cream,
    onSurface = Cream
)
private val LightColorScheme = lightColorScheme(
    primary = NavyBlue,
    secondary = Gold,
    tertiary = GoldLight,

    background = Cream,
    surface = CreamLight,

    onPrimary = Color.White,
    onSecondary = NavyBlue,
    onTertiary = NavyBlue,

    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun RescuePetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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