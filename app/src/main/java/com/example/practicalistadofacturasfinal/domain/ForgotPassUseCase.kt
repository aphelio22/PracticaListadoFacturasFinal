package com.example.practicalistadofacturasfinal.domain

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ForgotPassUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend operator fun invoke(email: String): Result<Unit> {
        return suspendCoroutine { continuation ->
            if (email.isEmpty()) {
                continuation.resume(Result.failure(Exception("El correo electrónico no puede estar vacío")))
            } else if (!isValidEmail(email)) {
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

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
}