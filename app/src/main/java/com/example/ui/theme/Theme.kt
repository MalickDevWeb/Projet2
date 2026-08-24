package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = CyanAccent,
  onPrimary = NavyDeep,
  primaryContainer = TechBlueContainer,
  onPrimaryContainer = CyanGlow,
  secondary = TechBlueLight,
  onSecondary = Color.White,
  secondaryContainer = NavyCard,
  onSecondaryContainer = Color.White,
  tertiary = AlertOrange,
  onTertiary = NavyDeep,
  background = DarkBackground,
  onBackground = Color.White,
  surface = DarkSurface,
  onSurface = Color.White,
  surfaceVariant = NavySurface,
  onSurfaceVariant = TextSecondaryLight,
  error = ErrorRed,
  onError = Color.White
)

private val LightColorScheme = lightColorScheme(
  primary = TechBlue,
  onPrimary = Color.White,
  primaryContainer = LightSurfaceVariant,
  onPrimaryContainer = NavyDeep,
  secondary = CyanAccent,
  onSecondary = Color.White,
  secondaryContainer = CyanContainer,
  onSecondaryContainer = Color.White,
  tertiary = AlertOrange,
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = TextPrimaryDark,
  surface = LightSurface,
  onSurface = TextPrimaryDark,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = TextSecondaryDark,
  error = ErrorRed,
  onError = Color.White
)

@Composable
fun SenAuraTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  SenAuraTheme(darkTheme = darkTheme, content = content)
}

