package com.example.practicalistadofacturasfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistadofacturasfinal.data.AppRepository
import com.example.practicalistadofacturasfinal.data.room.EnergyDataModelRoom
import kotlinx.coroutines.launch

class EnergyActivityViewModel: ViewModel() {
lateinit var appRepository: AppRepository

private val _energyDataLiveData = MutableLiveData<EnergyDataModelRoom>()
    val energyDataLiveData: LiveData<EnergyDataModelRoom>
        get() = _energyDataLiveData

    init {
        initRepository()
        fetchEnergyData()
    }

    private fun initRepository() {
        appRepository = AppRepository()
    }

    fun fetchEnergyData() {
        viewModelScope.launch {
            appRepository.fetchAndInsertEnergyDataFromMock()
            _energyDataLiveData.postValue(appRepository.getEnergyDataFromRoom())
        }
    }
}