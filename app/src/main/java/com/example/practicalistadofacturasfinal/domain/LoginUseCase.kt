package com.example.practicalistadofacturasfinal.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    suspend operator fun invoke(email: String?, password: String?): Result<FirebaseUser?> {
        return suspendCoroutine { continuation ->
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                continuation.resume(Result.failure(Exception("El correo electrónico o la contraseña no pueden estar vacíos")))
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            continuation.resume(Result.success(user))
                        } else {
//                            Log.d(
//                                "LoginUseCase",
//                                "Error al iniciar sesión: ${task.exception?.message}"
//                            )
                            continuation.resume(
                                Result.failure(
                                    task.exception
                                        ?: Exception("Error desconocido al iniciar sesión")
                                )
                            )
                        }
                    }
            }
        }
    }
}