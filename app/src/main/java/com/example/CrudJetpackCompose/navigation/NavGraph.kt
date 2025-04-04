package com.example.CrudJetpackCompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.CrudJetpackCompose.ui.HomeScreen
import com.example.CrudJetpackCompose.ui.LoginScreen
import com.example.CrudJetpackCompose.ui.SignUpScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }

        composable("signup") { SignUpScreen(navController) }
        composable("home") {
            HomeScreen()
        }
    }
}
