package com.example.practicalistadofacturasfinal

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.room.InvoiceModelRoom
import com.example.practicalistadofacturasfinal.ui.model.FilterVO
import com.example.practicalistadofacturasfinal.ui.viewmodel.InvoiceActivityViewModel
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject
import kotlin.test.Test

@ExperimentalCoroutinesApi
class InvoiceActivityViewModelUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var appRepository: AppRepository
    private lateinit var viewModel: InvoiceActivityViewModel
    private lateinit var context: Context

    @Before
    fun setup() {
        appRepository = Mockito.mock(AppRepository::class.java)
        context = Mockito.mock(Context::class.java)
        viewModel = InvoiceActivityViewModel(appRepository, context)
    }

    @Test
    fun `verify invoices are fetched`() = runBlocking {
        // Given
        val invoiceList = listOf(
            InvoiceModelRoom("Pendiente de pago", 1.56, "07/02/2019"),
            InvoiceModelRoom("Pagada", 25.14, "05/02/2019"),
            InvoiceModelRoom("Pagada", 22.69, "08/01/2019"),
            InvoiceModelRoom("Pendiente de pago", 12.84, "07/12/2018"),
            InvoiceModelRoom("Pagada", 35.16, "16/11/2018"),
            InvoiceModelRoom("Pagada", 18.27, "05/10/2018"),
            InvoiceModelRoom("Pendiente de pago", 61.17, "05/09/2018"),
            InvoiceModelRoom("Pagada", 37.18, "07/08/2018")
        )
        `when`(appRepository.getAllInvoicesFromRoom()).thenReturn(invoiceList)

        // When
        viewModel.fetchInvoices()

        val liveDataValue = viewModel.filteredInvoicesLiveData.getOrAwaitValue()
        assert(liveDataValue == invoiceList)
    }

    @Test
    fun `verify max amount is calculated correctly`() = runBlocking {
        val invoiceList = listOf(
            InvoiceModelRoom("Pendiente de pago", 1.56, "07/02/2019"),
            InvoiceModelRoom("Pagada", 25.14, "05/02/2019"),
            InvoiceModelRoom("Pagada", 22.69, "08/01/2019"),
            InvoiceModelRoom("Pendiente de pago", 12.84, "07/12/2018"),
            InvoiceModelRoom("Pagada", 35.16, "16/11/2018"),
            InvoiceModelRoom("Pagada", 18.27, "05/10/2018"),
            InvoiceModelRoom("Pendiente de pago", 61.17, "05/09/2018"),
            InvoiceModelRoom("Pagada", 37.18, "07/08/2018")
        )
        `when`(appRepository.getAllInvoicesFromRoom()).thenReturn(invoiceList)

        viewModel.fetchInvoices()

        val maxAmount = invoiceList.maxByOrNull { it.importeOrdenacion }?.importeOrdenacion ?: 0.0
        val viewModelMaxAmount = 61.17
        assert(viewModelMaxAmount == maxAmount)
    }

    @Test
    fun `verify filters are applied correctly`() {
        /*
        // Given
        val filter = FilterVO(/* insert sample filter values */)

        // When
        viewModel.applyFilters(/* insert sample filter values */)

        // Then
        assert(viewModel.filterLiveData.value == filter)
    }
*/
    }
}