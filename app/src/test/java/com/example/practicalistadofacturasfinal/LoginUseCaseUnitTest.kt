package com.example.practicalistadofacturasfinal


import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock

class LoginUseCaseUnitTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun `login with empty email should return error`() = runBlocking {
        val email = ""
        val password = "password"

        val result = loginUseCase.invoke(email, password)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico o la contraseña no pueden estar vacíos", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with empty password should return error`() = runBlocking {
        val email = "test@example.com"
        val password = ""

        val result = loginUseCase.invoke(email, password)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico o la contraseña no pueden estar vacíos", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with empty email and password should return error`() = runBlocking {
        val email = ""
        val password = ""

        val result = loginUseCase.invoke(email, password)

        assertTrue(result.isFailure)
        assertEquals("El correo electrónico o la contraseña no pueden estar vacíos", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with valid credentials should return user`() = runBlocking {
        val email = "jrgaln.m@gmail.com"
        val password = "Aviacion22"
        val firebaseUser: FirebaseUser = mock(FirebaseUser::class.java)
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).signInWithEmailAndPassword(email, password)
        doReturn(true).`when`(task).isSuccessful
        doReturn(firebaseUser).`when`(firebaseAuth).currentUser

        val result = loginUseCase.invoke(email, password)

        assertEquals(firebaseUser, result.getOrNull())
    }

    @Test
    fun `login with invalid credentials should not return user`() = runBlocking {
        val email = "correo_invalido@example.com"
        val password = "contraseña_invalida"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).signInWithEmailAndPassword(email, password)
        doReturn(false).`when`(task).isSuccessful

        val result = loginUseCase.invoke(email, password)
        assertNull(result.getOrNull())
    }

    @Test
    fun `login with Firebase failure should return error`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).signInWithEmailAndPassword(anyString(), anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(Exception("Firebase error")).`when`(task).exception

        val result = loginUseCase.invoke(email, password)

        assertTrue(result.isFailure)
        assertEquals("Firebase error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with network exception should return error`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val task: Task<AuthResult> = mock(Task::class.java) as Task<AuthResult>
        val networkException = Exception("Network error")

        doAnswer { invocation ->
            val listener = invocation.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }.`when`(task).addOnCompleteListener(any())

        doReturn(task).`when`(firebaseAuth).signInWithEmailAndPassword(anyString(), anyString())
        doReturn(false).`when`(task).isSuccessful
        doReturn(networkException).`when`(task).exception

        val result = loginUseCase.invoke(email, password)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}