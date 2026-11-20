package com.example.authexample.screens.signupscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.authexample.AuthState

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupScreenViewModel = viewModel() // this screen's own ViewModel
) {

    // Text typed by the user (local UI state)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authState = viewModel.authState

    // Runs every time authState changes:
    // - Authenticated -> go to home (and remove login/signup from the back stack with popUpTo)
    // - Error -> show the message in a Toast
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
            is AuthState.Error -> Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Signup", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = "Password") }
        )
        Spacer(modifier = Modifier.height(32.dp))

        // The button only asks the ViewModel to talk to Firebase
        Button(
            onClick = { viewModel.signup(email, password) },
            enabled = authState != AuthState.Loading
        ) {
            Text(text = "Signup")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Already have an account?")
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Login")
            }
        }
    }
}
