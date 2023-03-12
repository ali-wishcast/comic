package com.android.comic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
	primary = DarkPrimary,
	primaryVariant = DarkPrimaryVariant,
	secondary = DarkSecondary,
	secondaryVariant = DarkSecondaryVariant,
	background = DarkBackground,
	surface = DarkSurface,
	error = DarkError,
	onPrimary = DarkOnPrimary,
	onSecondary = DarkOnSecondary,
	onBackground = DarkOnBackground,
	onSurface = DarkOnSurface,
	onError = DarkOnError,
)

private val LightColorPalette = lightColors(
	primary = LightPrimary,
	primaryVariant = LightPrimaryVariant,
	secondary = LightSecondary,
	secondaryVariant = LightSecondaryVariant,
	background = LightBackground,
	surface = LightSurface,
	error = LightError,
	onPrimary = LightOnPrimary,
	onSecondary = LightOnSecondary,
	onBackground = LightOnBackground,
	onSurface = LightOnSurface,
	onError = LightOnError,
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
	val colors = if (darkTheme) {
		DarkColorPalette
	} else {
		LightColorPalette
	}

	MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content,
	)
}
