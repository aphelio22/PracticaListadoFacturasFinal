package com.example.practicalistadofacturasfinal

import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.practicalistadofacturasfinal.domain.SignUpUseCase
import com.example.practicalistadofacturasfinal.ui.viewmodel.SignUpActivityViewModel
import com.google.firebase.auth.FirebaseUser

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.Test
@ExperimentalCoroutinesApi
class SignUpViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var viewModel: SignUpActivityViewModel

    @Before
    fun setup() {
        signUpUseCase = mock(SignUpUseCase::class.java)
        viewModel = SignUpActivityViewModel(signUpUseCase)
    }

    @Test
    fun `verify signUp invokes use case and updates LiveData`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "patata"
        val confirmPassword = "patata"
        val firebaseUser = mock(FirebaseUser::class.java)
        val expectedResult = Result.success(firebaseUser)
        `when`(signUpUseCase.invoke(email, password, confirmPassword)).thenReturn(expectedResult)

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        // When
        viewModel.signUp(email, password, confirmPassword)

        // Then
        verify(signUpUseCase).invoke(email, password, confirmPassword)
        verify(observer).onChanged(expectedResult)
        viewModel.signUpResult.removeObserver(observer)
    }

    @Test
    fun `verify signUp handles use case error correctly`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "password"
        val expectedResult = Result.failure<FirebaseUser?>(Exception("Error"))
        `when`(signUpUseCase.invoke(email, password, confirmPassword)).thenReturn(expectedResult)

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        // When
        viewModel.signUp(email, password, confirmPassword)

        // Then
        verify(signUpUseCase).invoke(email, password, confirmPassword)
        verify(observer).onChanged(expectedResult)
        viewModel.signUpResult.removeObserver(observer)
    }

    @Test
    fun `verify signUp with missing email`() = runBlocking {
        val email = ""
        val password = "password"
        val confirmPassword = "password"
        val expectedResult = IllegalArgumentException("No hay email")

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        viewModel.signUp(email, password, confirmPassword)

        val actualResult = viewModel.signUpResult.value
        assertEquals(Result.failure<FirebaseUser?>(expectedResult).exceptionOrNull()?.message, actualResult?.exceptionOrNull()?.message)
        viewModel.signUpResult.removeObserver(observer)
    }

    @Test
    fun `verify signUp with missing password`() = runBlocking {
        val email = "test@example.com"
        val password = ""
        val confirmPassword = ""
        val expectedResult = IllegalArgumentException("Faltan las contraseñas")

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        viewModel.signUp(email, password, confirmPassword)

        val actualResult = viewModel.signUpResult.value
        assertEquals(Result.failure<FirebaseUser?>(expectedResult).exceptionOrNull()?.message, actualResult?.exceptionOrNull()?.message)
        viewModel.signUpResult.removeObserver(observer)
    }

    @Test
    fun `verify signUp with passwords not matching`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = "differentpassword"
        val expectedResult = IllegalArgumentException("Las contraseñas no coinciden")

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        // When
        viewModel.signUp(email, password, confirmPassword)

        // Then
        val actualResult = viewModel.signUpResult.value
        assertEquals(Result.failure<FirebaseUser?>(expectedResult).exceptionOrNull()?.message, actualResult?.exceptionOrNull()?.message)
        viewModel.signUpResult.removeObserver(observer)
    }

    @Test
    fun `verify signUp with missing confirm password`() = runBlocking {
        // Given
        val email = "test@example.com"
        val password = "password"
        val confirmPassword = ""
        val expectedResult = IllegalArgumentException("Falta la contraseña de confirmación")

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.signUpResult.observeForever(observer)

        // When
        viewModel.signUp(email, password, confirmPassword)

        // Then
        val actualResult = viewModel.signUpResult.value
        assertEquals(Result.failure<FirebaseUser?>(expectedResult).exceptionOrNull()?.message, actualResult?.exceptionOrNull()?.message)
        viewModel.signUpResult.removeObserver(observer)
    }
}