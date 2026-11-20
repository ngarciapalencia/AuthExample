package com.example.authexample.screens.signupscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.authexample.AuthState
import com.google.firebase.auth.FirebaseAuth

/**
 * BaaS (Firebase Authentication) - SIGNUP.
 */
class SignupScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var authState by mutableStateOf<AuthState>(AuthState.Unauthenticated)
        private set

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            authState = AuthState.Error("Email or password cannot be empty")
            return
        }
        authState = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                authState = if (task.isSuccessful) AuthState.Authenticated
                else AuthState.Error(task.exception?.message ?: "Something went wrong")
            }
    }
}
