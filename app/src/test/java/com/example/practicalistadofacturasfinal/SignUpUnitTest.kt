package com.example.practicalistadofacturasfinal

import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class SignUpUnitTest {

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
        val task: Task<*> = mock(Task::class.java)

        `when`(firebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(task as Task<AuthResult>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(true)
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        // Assert
        assertEquals(firebaseUser, result.getOrNull())
    }

    @Test
    fun `sign up with non-matching passwords should return error`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "wrongPassword"
        val task: Task<*> = mock(Task::class.java)

        `when`(firebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(task as Task<AuthResult>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(true)

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message == "Passwords do not match")
    }

    @Test
    fun `sign up with Firebase failure should return failure`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "password"
        val task: Task<*>? = mock(Task::class.java)

        `when`(firebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(task as Task<AuthResult>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.exception).thenReturn(Exception("Firebase error"))

        val result = signUpUseCase.invoke(email, password, confirmPassword)

        assertTrue(result.isFailure)
        assertEquals("Firebase error", result.exceptionOrNull()?.message)
    }
}