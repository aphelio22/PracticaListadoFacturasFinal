package com.example.practicalistadofacturasfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import com.example.practicalistadofacturasfinal.di.appModule
import com.example.practicalistadofacturasfinal.ui.viewmodel.EnergyActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.koin.core.context.stopKoin
import org.koin.core.context.startKoin
import org.koin.test.get
import io.mockk.coEvery
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EnergyActivityViewModelUnitTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: EnergyActivityViewModel

    @Before
    fun setup() {
        startKoin { modules(appModule) }
        viewModel = EnergyActivityViewModel()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `fetchEnergyData updates energy data LiveData`() = runBlocking {
        // Arrange
        val mockRepository: AppRepository = get()
        val energyData = EnergyDataModelRoom(
            cau = "ES0021000000001994LJ1FA000",
            requestStatus = "No hemos recibido ninguna solicitud de autoconsumo",
            selfConsumptionType = "Con excedentes y compensación Individual - Consumo",
            surplusCompensation = "Precio PVPC",
            installationPower = "5kWp"
        )
        coEvery { mockRepository.getEnergyDataFromRoom() } returns energyData

        // Act
        viewModel.fetchEnergyData()

        // Assert
        val result = viewModel.energyDataLiveData.getOrAwaitValue()
        assertEquals(energyData, result)
    }
}