package com.example.practicalistadofacturasfinal.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignUpUseCase {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, password: String, confirmPassword: String): Result<FirebaseUser?> {
        return suspendCoroutine { continuation ->
            if (password != confirmPassword) {
                continuation.resume(Result.failure(Exception("Passwords do not match")))
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            continuation.resume(Result.success(user))
                        } else {
                            continuation.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                        }
                    }
            }
        }
    }

}