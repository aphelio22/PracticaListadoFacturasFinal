package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.practicalistadofacturasfinal.domain.LoginUseCase
import com.example.practicalistadofacturasfinal.ui.viewmodel.LoginActivityViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.Test

class LoginViewModelUnitTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginActivityViewModel

    @Before
    fun setup() {
        loginUseCase = mock(LoginUseCase::class.java)
        viewModel = LoginActivityViewModel(loginUseCase)
    }

    @Test
    fun `verify login invokes use case and updates LiveData`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val firebaseUser = mock(FirebaseUser::class.java)
        val expectedResult = Result.success(firebaseUser)
        `when` (loginUseCase.invoke(email, password)).thenReturn(expectedResult)

        val observer = mock(Observer::class.java) as Observer<Result<FirebaseUser?>>
        viewModel.loginResult.observeForever(observer)

        viewModel.login(email, password)

        verify(loginUseCase).invoke(email, password)
        verify(observer).onChanged(expectedResult)
        viewModel.loginResult.removeObserver(observer)
    }

    @Test
    fun `isLoginInfoValid returns true when email and password are not empty`() = runBlocking {
        val email = "test@example.com"
        val password = "password"

        val result = viewModel.isLoginInfoValid(email, password)

        assert(result)
    }

    @Test
    fun `isLoginInfoValid returns false for empty email`() {
        val email = ""
        val password = "password"

        val result = viewModel.isLoginInfoValid(email, password)

        assert(!result)
    }

    @Test
    fun `isLoginInfoValid returns false for empty password`() {
        val email = "test@example.com"
        val password = ""

        val result = viewModel.isLoginInfoValid(email, password)

        assert(!result)
    }

    @Test
    fun `isLoginInfoValid returns false for empty email and password`() {
        val email = ""
        val password = ""

        val result = viewModel.isLoginInfoValid(email, password)

        assert(!result)
    }
}