package com.example.authexample

// The possible states of the authentication (shared by the login, signup and home view models)
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
