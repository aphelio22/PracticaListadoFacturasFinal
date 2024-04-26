package com.example.practicalistadofacturasfinal.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginUseCase {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        continuation.resume(Result.success(user))
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Error")))
                    }
                }
        }
    }
}