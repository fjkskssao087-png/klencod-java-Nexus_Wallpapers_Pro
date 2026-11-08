package com.nexus.wallpapers.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF0A84FF),
    background = Color(0xFF000000),
    surface = Color(0xFF0A0A0F),
    onPrimary = Color.White,
    onBackground = Color(0xFFF5F5F7)
)

@Composable
fun NexusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColors, content = content)
}