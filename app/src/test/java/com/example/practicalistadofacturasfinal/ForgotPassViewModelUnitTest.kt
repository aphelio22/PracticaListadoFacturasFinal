package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.domain.ForgotPassUseCase
import com.example.practicalistadofacturasfinal.ui.viewmodel.ForgotPassViewModel
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class ForgotPassViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var forgotPassUseCase: ForgotPassUseCase
    private lateinit var viewModel: ForgotPassViewModel

    @Before
    fun setup() {
        forgotPassUseCase = Mockito.mock(ForgotPassUseCase::class.java)
        viewModel = ForgotPassViewModel(forgotPassUseCase)
    }

    @Test
    fun `verify resetPassword invokes use case and updates LiveData`() = runBlocking {
        // Given
        val email = "test@example.com"
        val expectedResult = Result.success(Unit)
        `when`(forgotPassUseCase.invoke(email)).thenReturn(expectedResult)

        val observer = mock(Observer::class.java) as Observer<Result<Unit>>
        viewModel.resetPassResult.observeForever(observer)

        // When
        viewModel.resetPassword(email)

        // Then
        verify(forgotPassUseCase).invoke(email)
        verify(observer).onChanged(expectedResult)
        viewModel.resetPassResult.removeObserver(observer)
    }

    @Test
    fun `isEmailValid should return true for non-empty email`() {
        val email = "test@example.com"
        val result = viewModel.isEmailValid(email)
        assertTrue(result)
    }

    @Test
    fun `isEmailValid should return false for empty email`() {
        // Given
        val email = ""

        // When
        val result = viewModel.isEmailValid(email)

        // Then
        assertFalse(result)
    }
}