package com.example.practicalistadofacturasfinal.domain

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ForgotPassUseCase(private val firebaseAuth: FirebaseAuth) {

    suspend operator fun invoke(email: String): Result<Unit> {
        return suspendCoroutine { continuation ->
            if (email.isEmpty()) {
                continuation.resume(Result.failure(Exception("El correo electrónico no puede estar vacío")))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                continuation.resume(Result.failure(Exception("El correo electrónico no es válido")))
            } else {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(Result.success(Unit))
                        } else {
                            continuation.resume(Result.failure(task.exception ?: Exception("Error desconocido al restablecer la contraseña")))
                        }
                    }
            }
        }
    }
}