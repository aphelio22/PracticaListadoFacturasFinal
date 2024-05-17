package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class InvoiceActivityViewModelUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: InvoiceActivityViewModel
    private lateinit var mockRepository: AppRepository

    @Before
    fun setup() {
        mockRepository = mock(AppRepository::class.java)
        viewModel = InvoiceActivityViewModel()
        viewModel.appRepository = mockRepository
    }

    @Test
    fun `initRepository initializes repository`() {
        // Arrange: Ya se realizó en el método setup()

        // Act: Llamada al método que queremos probar
        viewModel.initRepository()

        // Assert: Verificar que el repositorio no sea nulo después de la inicialización
        assertNotNull(viewModel.appRepository)
    }
}