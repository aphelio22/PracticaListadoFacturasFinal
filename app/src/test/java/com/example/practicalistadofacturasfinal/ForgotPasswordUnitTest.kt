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
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doReturn
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
        val task: Task<Void> = mock(Task::class.java) as Task<Void>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).sendPasswordResetEmail(anyString())
        doReturn(true).`when`(task).isSuccessful

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `forgot password with Firebase failure should return failure`() = runBlocking {
        val email = "test@example.com"
        val task: Task<Void> = mock(Task::class.java) as Task<Void>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).sendPasswordResetEmail(anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(Exception("Firebase error")).`when`(task).exception

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("Firebase error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `forgot password with invalid email should return error`() = runBlocking {
        val email = "invalid-email"

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico no es válido", result.exceptionOrNull()?.message)
    }

    @Test
    fun `forgot password with Firebase network exception should return failure`() = runBlocking {
        val email = "test@example.com"
        val task: Task<Void> = mock(Task::class.java) as Task<Void>
        val networkException = Exception("Network error")

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).sendPasswordResetEmail(anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(networkException).`when`(task).exception

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `forgot password with rate limit exceeded should return failure`() = runBlocking {
        val email = "test@example.com"
        val task: Task<Void> = mock(Task::class.java) as Task<Void>
        val rateLimitException = Exception("Rate limit exceeded")

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).sendPasswordResetEmail(anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(rateLimitException).`when`(task).exception

        val result = forgotPassUseCase.invoke(email)

        assertTrue(result.isFailure)
        assertEquals("Rate limit exceeded", result.exceptionOrNull()?.message)
    }
}