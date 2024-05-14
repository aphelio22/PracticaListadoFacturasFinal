package com.example.practicalistadofacturasfinal.domain

import com.google.firebase.auth.FirebaseAuth

class LogOutUseCase(private val firebaseAuth: FirebaseAuth) {

    operator fun invoke() {
        firebaseAuth.signOut()
    }
}