package com.example.authexample.screens.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.authexample.AuthState

/**
 * STATE: this is the STATEFUL composable (the owner of the state).
 * The two counters live here and are passed DOWN to CounterContent (state hoisting).
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel() // this screen's own ViewModel (logout)
) {

    // Counter 1: remember -> value is LOST when the screen is rotated (configuration change)
    var normalCounter by remember { mutableStateOf(0) }

    // Counter 2: rememberSaveable -> value SURVIVES rotation
    var savedCounter by rememberSaveable { mutableStateOf(0) }

    // If the user logs out, go back to the login screen
    LaunchedEffect(viewModel.authState) {
        if (viewModel.authState is AuthState.Unauthenticated) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Data goes DOWN (the two values), events go UP (onIncrement lambda)
        CounterContent(
            normalCount = normalCounter,
            savedCount = savedCounter,
            onIncrement = {
                // ONE button increments BOTH counters
                normalCounter++
                savedCounter++
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = { viewModel.signOut() }) {
            Text(text = "Logout")
        }
    }
}

/**
 * STATELESS composable: it owns no state, it only shows what it receives
 * and tells the parent when the button is pressed.
 */
@Composable
fun CounterContent(normalCount: Int, savedCount: Int, onIncrement: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "remember: $normalCount", fontSize = 24.sp)
        Text(text = "rememberSaveable: $savedCount", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onIncrement) {
            Text(text = "Increment both")
        }
    }
}
