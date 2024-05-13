package com.example.practicalistadofacturasfinal

import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.mock

class SignUpUnitTest {

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        signUpUseCase = SignUpUseCase(firebaseAuth)
    }



}