package com.stogramm.composetest3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.ironmeddie.registerscreen.registration.registration_main_info.white

private val DarkColorPalette = darkColors(
    primary = Teal200,
    primaryVariant = maindarckBlue,
    secondary = white,
    background = Black,
    onBackground = white
)

private val LightColorPalette = lightColors(
    primary = AppGreen,
    primaryVariant = AppGreenDark,
    secondary = GreyMain,
    background = White100,
    onBackground = Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Composetest3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}