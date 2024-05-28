package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.domain.LogOutUseCase
import com.example.practicalistadofacturasfinal.ui.viewmodel.SelectionActivityMViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class SelectionActivityMViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var logOutUseCase: LogOutUseCase
    private lateinit var viewModel: SelectionActivityMViewModel

    @Before
    fun setup() {
        logOutUseCase = mock(LogOutUseCase::class.java)
        viewModel = SelectionActivityMViewModel(logOutUseCase)
    }

    @Test
    fun `verify logOut invokes logOutUseCase`() = runTest {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel.logOut()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(logOutUseCase).invoke()
        Dispatchers.resetMain()
    }
}