package com.example.authexample.screens.loginscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.authexample.AuthState
import com.google.firebase.auth.FirebaseAuth

/**
 * BaaS (Firebase Authentication) - LOGIN.
 * The ViewModel talks to Firebase; the screen only reads [authState] and calls login().
 */
class LoginScreenViewModel : ViewModel() {

    // Firebase Authentication object (the "backend" we do not have to write ourselves)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // mutableStateOf -> Compose recomposes when it changes.
    // "private set" -> only this class can change it, the screen can only read it.
    var authState by mutableStateOf<AuthState>(AuthState.Unauthenticated)
        private set

    init {
        checkAuthStatus()
    }

    // Firebase remembers the user between app launches, so we check if somebody is already logged in
    private fun checkAuthStatus() {
        authState = if (auth.currentUser == null) AuthState.Unauthenticated
        else AuthState.Authenticated
    }

    fun login(email: String, password: String) {
        // Simple validation before calling Firebase
        if (email.isEmpty() || password.isEmpty()) {
            authState = AuthState.Error("Email or password cannot be empty")
            return
        }
        authState = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                authState = if (task.isSuccessful) AuthState.Authenticated
                else AuthState.Error(task.exception?.message ?: "Something went wrong")
            }
    }
}
