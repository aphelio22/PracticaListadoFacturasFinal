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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnergyThirdBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.energyDataLiveData.observe(viewLifecycleOwner) { energyData ->
            binding.etCauContent.setText(energyData.cau)
            binding.etRequestStatusContent.setText(energyData.requestStatus)
            binding.etSelfConsumptionContent.setText(energyData.selfConsumptionType)
            binding.etSurplusCompensationContent.setText(energyData.surplusCompensation)
            binding.etInstallationPowerContent.setText(energyData.installationPower)
        }

        binding.etRequestStatus.setEndIconOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val customPopupFragment = FragmentPopUp("El tiempo estimado de activación de tu autoconsumo es de 1 a 2 meses, este variará en función de tu comunidad autónoma y distribuidora.")
            customPopupFragment.show(fragmentManager, "FragmentPopUp")
        }
    }
}