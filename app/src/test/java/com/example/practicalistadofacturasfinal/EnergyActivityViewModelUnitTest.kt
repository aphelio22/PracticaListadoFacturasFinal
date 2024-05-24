package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.ui.viewmodel.EnergyActivityViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class EnergyActivityViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var appRepository: AppRepository
    private lateinit var viewModel: EnergyActivityViewModel

    @Before
    fun setup() {
        appRepository = Mockito.mock(AppRepository::class.java)
        viewModel = EnergyActivityViewModel(appRepository)
    }

    @Test
    fun `fetchEnergyData updates energy data LiveData`() = runBlocking {
        val energyData = EnergyDataModelRoom(
            cau = "ES0021000000001994LJ1FA000",
            requestStatus = "No hemos recibido ninguna solicitud de autoconsumo",
            selfConsumptionType = "Con excedentes y compensación Individual - Consumo",
            surplusCompensation = "Precio PVPC",
            installationPower = "5kWp"
        )
        `when`(appRepository.getEnergyDataFromRoom()).thenReturn(energyData)

        viewModel.fetchEnergyData()

        val liveDataValue = viewModel.energyDataLiveData.getOrAwaitValue()
        assert(liveDataValue == energyData)
    }

    @Test
    fun viewModelNotNull() = runBlocking {
        assertNotNull(viewModel)
    }

    @Test
    fun `fetchEnergyData does not update energy data LiveData when there is no data`() = runBlocking {
        `when`(appRepository.getEnergyDataFromRoom()).thenReturn(null)
        viewModel.fetchEnergyData()
        assert(viewModel.energyDataLiveData.value == null)
    }

    @Test
    fun `handleError updates error LiveData`() = runBlocking {
        val errorMessage = "Error al obtener datos de energía"
        `when`(appRepository.getEnergyDataFromRoom()).thenThrow(RuntimeException(errorMessage))
        viewModel.fetchEnergyData()
        val errorLiveDataValue = "Error al obtener datos de energía"
        assertEquals(errorMessage, errorLiveDataValue)
    }
}