package com.example.practicalistadofacturasfinal

import com.example.practicalistadofacturasfinal.domain.LogOutUseCase
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class LogOutUnitTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logOutUseCase: LogOutUseCase

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        logOutUseCase = LogOutUseCase(firebaseAuth)
    }

    @Test
    fun `log out should call signOut on FirebaseAuth`() {
        logOutUseCase.invoke()
        verify(firebaseAuth).signOut()
    }
}