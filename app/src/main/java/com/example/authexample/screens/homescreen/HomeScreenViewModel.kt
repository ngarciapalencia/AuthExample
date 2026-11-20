package com.example.authexample.screens.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.authexample.AuthState
import com.google.firebase.auth.FirebaseAuth

/**
 * BaaS (Firebase Authentication) - LOGOUT.
 *
 * NOTE: the two counters are NOT in this ViewModel on purpose. A ViewModel survives rotation,
 * so both counters would survive and we could not show the difference between
 * remember and rememberSaveable. They stay in HomeScreen.
 */
class HomeScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Authenticated while a user is logged in; becomes Unauthenticated after signOut()
    var authState by mutableStateOf<AuthState>(
        if (auth.currentUser == null) AuthState.Unauthenticated else AuthState.Authenticated
    )
        private set

    fun signOut() {
        auth.signOut()
        authState = AuthState.Unauthenticated
    }
}
