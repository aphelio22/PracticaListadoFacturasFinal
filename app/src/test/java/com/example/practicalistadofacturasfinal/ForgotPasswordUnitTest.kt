package com.example.practicalistadofacturasfinal

import com.example.practicalistadofacturasfinal.domain.ForgotPassUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ForgotPasswordUnitTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var forgotPassUseCase: ForgotPassUseCase

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        forgotPassUseCase = ForgotPassUseCase(firebaseAuth)
    }

    @Test
    fun `forgot password with empty email should return error`() = runBlocking {
        val email = ""

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico no puede estar vacío", result.exceptionOrNull()?.message)
    }

    @Test
    fun `forgot password with Firebase success should return success`() = runBlocking {
        val email = "test@example.com"
        val task: Task<*>? = mock(Task::class.java)

        `when`(firebaseAuth.sendPasswordResetEmail(anyString())).thenReturn(task as Task<Void>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(true)

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `forgot password with Firebase failure should return failure`() = runBlocking {
        val email = "test@example.com"
        val task: Task<*> = mock(Task::class.java)

        `when`(firebaseAuth.sendPasswordResetEmail(anyString())).thenReturn(task as Task<Void>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.exception).thenReturn(Exception("Firebase error"))

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("Firebase error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `forgot password with invalid email should return success`() = runBlocking {
        val email = "invalid-email"
        val task: Task<*> = mock(Task::class.java)

        `when`(firebaseAuth.sendPasswordResetEmail(anyString())).thenReturn(task as Task<Void>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(true)

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isSuccess)
    }
}