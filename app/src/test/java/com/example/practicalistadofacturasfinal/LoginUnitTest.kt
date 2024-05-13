package com.example.practicalistadofacturasfinal


import android.util.Log
import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.math.truncate

class LoginUnitTest {
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun `login with valid credentials should return user`() = runBlocking {
        // Arrange
        val email = "jrgaln.m@gmail.com"
        val password = "Aviacion22"
        val firebaseUser: FirebaseUser = mock(FirebaseUser::class.java)
        val task: Task<*> = mock(Task::class.java)

        // AQUI EXISTE
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(task as Task<AuthResult>)
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        `when`(task.isSuccessful).thenReturn(true)
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)

        // Act
        val result = loginUseCase.invoke(email, password)


        Assert.assertEquals(firebaseUser, result.getOrNull())
    }

    @Test
    fun `login with invalid credentials should not return user`() = runBlocking {
        // Arrange
        val email = "jrgaln.m@gmail.com"
        val password = "gfxf"
        val task: Task<*> = mock(Task::class.java)

        `when`(loginUseCase(email, password)).thenReturn(Result.failure(Exception("El correo electrónico o la contraseña no pueden estar vacíos")))
        `when`(task.addOnCompleteListener(any())).thenAnswer { invocation ->
            val listener = invocation.getArgument<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task as Task<AuthResult>)
            task
        }
        `when`(task.isSuccessful).thenReturn(false)

        // Act
        val result = loginUseCase.invoke(email, password)

        // Assert
        Assert.assertNull(result.getOrNull())
    }
}