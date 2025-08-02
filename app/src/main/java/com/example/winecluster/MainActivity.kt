package com.example.winecluster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.winecluster.ui.theme.WineClusterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WineClusterApp()
        }
    }
}

@Composable
fun WineClusterApp() {
    WineClusterTheme {
        Surface(color = Color(0xFF841B3A)) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "entry") {
                composable("entry") {
                    EntryScreen(onStartClicked = {
                        navController.navigate("input")
                    })
                }
                composable("input") {
                    InputScreen()
                }
            }
        }
    }
}
