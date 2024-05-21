package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EnergyActivityViewModel : ViewModel(), KoinComponent {

    private val appRepository: AppRepository by inject()

private val _energyDataLiveData = MutableLiveData<EnergyDataModelRoom>()
    val energyDataLiveData: LiveData<EnergyDataModelRoom>
        get() = _energyDataLiveData

    init {
        fetchEnergyData()
    }

    fun fetchEnergyData() {
        viewModelScope.launch {
            appRepository.fetchAndInsertEnergyDataFromMock()
            _energyDataLiveData.postValue(appRepository.getEnergyDataFromRoom())
        }
    }
}