package com.example.practicalistadofacturasfinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.practicalistadofacturasfinal.R
import com.example.practicalistadofacturasfinal.databinding.FragmentEnergyThirdBinding
import com.example.practicalistadofacturasfinal.ui.viewmodel.EnergyActivityViewModel

class EnergyThirdFragment : Fragment() {
    private lateinit var binding: FragmentEnergyThirdBinding
    private val viewModel: EnergyActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEnergyThirdBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.energyDataLiveData.observe(viewLifecycleOwner) { energyData ->
            binding.etCau.setText(energyData.cau)
            binding.etRequestStatus.setText(energyData.requestStatus)
            binding.etSelfConsumption.setText(energyData.selfConsumptionType)
            binding.etSurplusCompensation.setText(energyData.surplusCompensation)
            binding.etInstallationPower.setText(energyData.installationPower)
        }

    }
}