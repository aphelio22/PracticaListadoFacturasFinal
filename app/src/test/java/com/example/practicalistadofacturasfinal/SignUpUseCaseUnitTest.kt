package com.example.practicalistadofacturasfinal

import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

class SignUpUseCaseUnitTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        signUpUseCase = SignUpUseCase(firebaseAuth)
    }

    @Test
    fun `sign up with matching passwords should return user`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "password"
        val firebaseUser: FirebaseUser = mock(FirebaseUser::class.java)
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())
        doReturn(true).`when`(task).isSuccessful
        doReturn(firebaseUser).`when`(firebaseAuth).currentUser

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertEquals(firebaseUser, result.getOrNull())
    }

    @Test
    fun `sign up with non-matching passwords should return error`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "wrongPassword"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())
        doReturn(true).`when`(task).isSuccessful

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("Passwords do not match", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sign up with Firebase failure should return failure`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "password"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(Exception("Firebase error")).`when`(task).exception

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("Firebase error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sign up with network exception should return error`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "password"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>
        val networkException = Exception("Network error")

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(networkException).`when`(task).exception

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sign up with empty email should return error`() = runBlocking {
        val email = ""
        val password = "password"
        val confirmPassword = "password"

        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico no puede estar vacío", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sign up with empty password should return error`() = runBlocking {
        val email = "test@example.com"
        val password = ""
        val confirmPassword = ""

        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).createUserWithEmailAndPassword(anyString(), anyString())

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("La contraseña no puede estar vacía", result.exceptionOrNull()?.message)
    }
}