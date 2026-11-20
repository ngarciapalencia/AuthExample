package com.example.authexample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authexample.screens.homescreen.HomeScreen
import com.example.authexample.screens.loginscreen.LoginScreen
import com.example.authexample.screens.signupscreen.SignupScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // Three screens: login, signup and home (the counters).
    // Each screen creates its own ViewModel (see each screen folder).
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(modifier, navController) }
        composable("signup") { SignupScreen(modifier, navController) }
        composable("home") { HomeScreen(modifier, navController) }
    }
}
