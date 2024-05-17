package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.ui.viewmodel.EnergyActivityViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EnergyActivityViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: AppRepository

    @Mock
    private lateinit var myApplication: MyApplication

    private lateinit var viewModel: EnergyActivityViewModel

    @Before
    fun setup() {
        myApplication = mock(MyApplication::class.java)
        mockRepository = mock(AppRepository::class.java)
        viewModel = EnergyActivityViewModel().apply {
            appRepository = mockRepository
        }
    }

    @Test
    fun `fetchEnergyData updates energy data state flow`() = runBlocking {
        // Arrange
        val energyData = EnergyDataModelRoom(
            cau = "ES0021000000001994LJ1FA000",
            requestStatus = "No hemos recibido ninguna solicitud de autoconsumo",
            selfConsumptionType = "Con excedentes y compensación Individual - Consumo",
            surplusCompensation = "Precio PVPC",
            installationPower = "5kWp"
        )
        `when`(mockRepository.getEnergyDataFromRoom()).thenReturn(energyData)

        // Act
        viewModel.fetchEnergyData()

        // Assert
        val result = viewModel.energyDataStateFlow.first()
        assertEquals(energyData, result)
    }
}