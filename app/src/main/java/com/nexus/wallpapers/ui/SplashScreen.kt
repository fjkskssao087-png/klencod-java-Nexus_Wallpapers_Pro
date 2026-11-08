package com.nexus.wallpapers.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var animate by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (animate) 1f else 0f, tween(1200), label = "a")
    val offset by animateFloatAsState(if (animate) 0f else 50f, tween(1200), label = "o")

    LaunchedEffect(Unit) {
        animate = true
        delay(2200)
        navController.navigate("home") { popUpTo("splash") { inclusive = true } }
    }

    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF0A0A0F), Color.Black))), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.alpha(alpha).offset(y = offset.dp)) {
            Text("Nexus", fontSize = 64.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = (-2).sp)
            Text("4K WALLPAPERS", fontSize = 14.sp, color = Color(0xFF0A84FF), letterSpacing = 8.sp, fontWeight = FontWeight.Bold)
        }
    }
}