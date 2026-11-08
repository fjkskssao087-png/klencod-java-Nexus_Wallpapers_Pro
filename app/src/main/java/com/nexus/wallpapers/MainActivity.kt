package com.nexus.wallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexus.wallpapers.ui.theme.NexusTheme
import com.nexus.wallpapers.ui.SplashScreen
import com.nexus.wallpapers.ui.HomeScreen
import com.nexus.wallpapers.ui.DetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") { SplashScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("detail/{imageUrl}") { backStackEntry ->
                        val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                        DetailScreen(imageUrl, navController)
                    }
                }
            }
        }
    }
}