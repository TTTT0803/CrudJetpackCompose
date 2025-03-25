package com.example.CrudJetpackCompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.CrudJetpackCompose.ui.HomeScreen
import com.example.CrudJetpackCompose.ui.LoginScreen
import com.example.CrudJetpackCompose.ui.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("signup") { SignUpScreen(navController) }
                composable("home") { HomeScreen() }
            }
        }
    }
}

